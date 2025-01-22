package com.example.spb4you.controllers;

import com.example.spb4you.models.Location;
import com.example.spb4you.models.Route;
import com.example.spb4you.services.LocationService;
import com.example.spb4you.services.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.spb4you.models.User;
import com.example.spb4you.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

    private static final String uploadDir = "elements/avatars"; // Путь до папки для аватаров

    // Метод для обновления информации о пользователе
    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer userId,
            @RequestParam String username,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) MultipartFile photo) {

        User user = userService.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден"); // обрабатываем ситуацию, когда пользователь не найден
        }

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (photo != null && !photo.isEmpty()) {
            // Здесь добавьте код для загрузки изображения, аналогичный методу uploadPhoto
            // Пример кода можно взять из вашего метода uploadPhoto
            String fileName = "ava" + userId + ".png";
            Path path = Paths.get(uploadDir, fileName);
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, photo.getBytes());
                user.setAvatarPath(fileName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при загрузке фото");
            }
        }

        userService.save(user);
        return ResponseEntity.ok(user);
    }


    // Метод для загрузки фотографии
    @PostMapping("/{userId}/uploadPhoto")
    public ResponseEntity<?> uploadPhoto(
            @PathVariable Integer userId,
            @RequestParam MultipartFile photo) {

        User user = userService.findById(userId).orElse(null);

        try {
            // Создаем новое имя файла
            String fileName = "ava" + userId + ".png";

            // Путь для сохранения
            Path path = Paths.get(uploadDir, fileName);
            Files.createDirectories(path.getParent()); // Создаем директории, если они не существуют

            // Сохраняем файл
            Files.write(path, photo.getBytes());

            // Сохраняем путь к аватару в БД
            assert user != null;
            user.setAvatarPath(fileName);
            userService.save(user);

            return ResponseEntity.ok(user); // Возвращаем обновленного пользователя с атрибутом avatarPath
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo");
        }
    }


    @GetMapping("/info/list")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAll()
                .stream()
                .filter(user -> "Пользователь".equals(user.getRole()))
                .toList();
        return ResponseEntity.ok(users); // Возвращаем список всех пользователей со всеми полями для каждого с кодом 200
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable("userId") Integer userId) {
        // Находим пользователя по ID
        User user = userService.findById(userId).orElse(null);

        // Проверяем, что пользователь найден и имеет роль "user"
        if (user != null && "Пользователь".equals(user.getRole())) {
            return ResponseEntity.ok(user); // Возвращаем набор полей пользователя с кодом 200
        }

        // Если пользователь не найден или не имеет правильную роль, возвращаем статус 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Возвращаем 404 Not Found
    }


    @GetMapping("/registry")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Создание нового объекта User
        return "registration";
    }

    @PostMapping("/registry")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Проверка на существующего пользователя
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("errorMessage", "Ошибка: Пользователь с таким email уже существует.");
            return "registration"; // Возвращение на страницу регистрации с сообщением об ошибке
        }

        // Хэширование пароля (для безопасности)

        // Сохранение нового пользователя в БД
        user.setRegistrationDate(LocalDateTime.now());
        user.setLikedLocations(", ");
        user.setLikedRoutes(", ");
        user.setRole("Пользователь");
        userService.save(user);

        model.addAttribute("username", user.getUsername()); // Сохраняем имя пользователя в модели
        // Перенаправление на личную страницу пользователя
        // return "redirect:/users/index/" + user.getId(); // Используем ID для формирования URL
        return "redirect:/users/registry-success/" + user.getId();
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User()); // Создаем новый объект User в модели
        return "auth"; // Возвращаем страницу входа
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        // Проверка на существующего пользователя
        User existingUser = userService.findByUsername(user.getUsername()).orElse(null);
// ВАЖНО - добавить обработку при совпадении имён
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("errorMessage", "Ошибка: Неверное имя пользователя или пароль");
            return "auth"; // Возвращаем на страницу логина с сообщением об ошибке
        }

        // Сохраняем id пользователя в сессии
        session.setAttribute("userId", existingUser.getId());

        // Перенаправление на главную страницу
        if ("Пользователь".equals(existingUser.getRole())) {
            return "redirect:/users/index/" + existingUser.getId(); // Используем ID для формирования URL
        } else if ("Администратор".equals(existingUser.getRole())) {
            return "redirect:/admins/index/" + existingUser.getId();
        } else {
            return "404"; // Возвращаем ошибку
        }
    }

    /*
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login"; // Перенаправление на страницу логина после выхода
    }
    */

    @GetMapping("/registry-success/{userId}")
    public String registrySuccess(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "404"; // Возвращаем ошибку
        }
        model.addAttribute("userId", userId);
        return "registry_success"; // Уведомление об успешной регистрации
    }

    @GetMapping("/index/{userId}")
    public String userInfo(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "404"; // Возвращаем ошибку
        }
        model.addAttribute("user", user);
        return "index"; // Главная страница зарегистрированного пользователя
    }

    @GetMapping("/cabinet/{userId}")
    public String userCabinet(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "404"; // Возвращаем ошибку
        }

        // Форматируем дату в нужный формат
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = user.getRegistrationDate().format(formatter);

        // Добавляем отформатированную дату в модель
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("user", user);
        return "cabinet"; // Личный кабинет зарегистрированного пользователя
    }

    @GetMapping("/{userId}/likes")
    public String userShowLikedPage(@PathVariable("userId") Integer userId) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "404"; // Возвращаем ошибку
        }
        return "likedPage"; // Страница с избранными локациями и маршрутами
    }

    @PostMapping("/{userId}/like-locations/{locationId}")
    public ResponseEntity<?> likeLocation(@PathVariable Integer userId, @PathVariable Integer locationId) {
        try {
            System.out.println("Attempting to like location ID: " + locationId + " for user ID: " + userId);

            // Получение пользователя
            User user = userService.findById(userId).orElse(null);
            if (user == null) {
                System.out.println("User not found!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            // ПолучениеLikedLocations
            List<Integer> likedLocations = user.getLikedLocationsList();
            if (likedLocations == null) {
                likedLocations = new ArrayList<>(); // Инициализация списка
            }

            System.out.println("Current liked locations: " + likedLocations);

            // Проверка наличия лайка
            if (!likedLocations.contains(locationId)) {
                likedLocations.add(locationId);
                user.setLikedLocationsList(likedLocations);
                userService.save(user); // Сохраняем пользователя

                // Попытка получить локацию
                Location location = locationService.findById(locationId).orElse(null);
                if (location != null) {
                    location.setLikes(location.getLikes() + 1);
                    locationService.save(location);
                    System.out.println("Location liked successfully.");
                    return ResponseEntity.ok("Location liked.");
                } else {
                    System.out.println("Location not found!");
                    return ResponseEntity.badRequest().body("Location not found.");
                }
            } else {
                System.out.println("Location already liked.");
                return ResponseEntity.badRequest().body("Location already liked.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Печать информации об ошибке
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/{userId}/unlike-locations/{locationId}")
    public ResponseEntity<?> unlikeLocation(@PathVariable Integer userId, @PathVariable Integer locationId) {
        System.out.println("Attempting to unlike location ID: " + locationId + " for user ID: " + userId);

        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Получаем список LikedLocations
        List<Integer> likedLocations = user.getLikedLocationsList();
        if (likedLocations == null) {
            likedLocations = new ArrayList<>(); // Возможно, список не был инициализирован
        }

        System.out.println("Current liked locations: " + likedLocations);

        // Логика удаления лайка с локации
        if (likedLocations.contains(locationId)) {
            likedLocations.remove(locationId);
            user.setLikedLocationsList(likedLocations);
            if (user.getLikedLocations() == null || user.getLikedLocations().trim().isEmpty()) {
                user.setLikedLocations(", ");
            }
            userService.save(user); // Сохраняем изменения пользователя

            // Уменьшаем параметр likes локации
            Location location = locationService.findById(locationId).orElse(null);
            if (location != null) {
                location.setLikes(location.getLikes() - 1);
                locationService.save(location); // Сохраняем изменения локации
                System.out.println("Location unliked successfully.");
                return ResponseEntity.ok("Location unliked.");
            } else {
                System.out.println("Location not found!");
                return ResponseEntity.badRequest().body("Location not found.");
            }
        }

        System.out.println("Location was not liked by the user.");
        return ResponseEntity.badRequest().body("Location not liked yet.");
    }

    @GetMapping("/{userId}/like-locations")
    public String userShowLocationsLikedPage(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "404"; // Возвращаем ошибку
        }

        // Получаем список идентификаторов локаций, которые пользователь добавил в избранное
        List<Integer> likedLocationIds = user.getLikedLocationsList();

        // Фильтруем локации, чтобы оставить только те, которые есть в избранном у пользователя
        List<Location> likedLocations = locationService.findAll()
                .stream()
                .filter(location -> likedLocationIds.contains(location.getId())) // Проверяем наличие id в списке
                .toList();

        model.addAttribute("likedLocations", likedLocations);
        model.addAttribute("userId", userId);
        return "likedLocationsPage"; // Страница с избранными локациями
    }

    @PostMapping("/{userId}/like-routes/{routeId}")
    public ResponseEntity<?> likeRoute(@PathVariable Integer userId, @PathVariable Integer routeId) {
        try {
            System.out.println("Attempting to like route ID: " + routeId + " for user ID: " + userId);

            // Получение пользователя
            User user = userService.findById(userId).orElse(null);
            if (user == null) {
                System.out.println("User not found!");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            // ПолучениеLikedRoutes
            List<Integer> likedRoutes = user.getLikedRoutesList();
            if (likedRoutes == null) {
                likedRoutes = new ArrayList<>(); // Инициализация списка
            }

            System.out.println("Current liked routes: " + likedRoutes);

            // Проверка наличия лайка
            if (!likedRoutes.contains(routeId)) {
                likedRoutes.add(routeId);
                user.setLikedRoutesList(likedRoutes);
                userService.save(user); // Сохраняем пользователя

                // Попытка получить локацию
                Route route = routeService.findById(routeId).orElse(null);
                if (route != null) {
                    route.setLikes(route.getLikes() + 1);
                    routeService.save(route);
                    System.out.println("Route liked successfully.");
                    return ResponseEntity.ok("Route liked.");
                } else {
                    System.out.println("Route not found!");
                    return ResponseEntity.badRequest().body("Route not found.");
                }
            } else {
                System.out.println("Route already liked.");
                return ResponseEntity.badRequest().body("Route already liked.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Печать информации об ошибке
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @PostMapping("/{userId}/unlike-routes/{routeId}")
    public ResponseEntity<?> unlikeRoute(@PathVariable Integer userId, @PathVariable Integer routeId) {
        System.out.println("Attempting to unlike route ID: " + routeId + " for user ID: " + userId);

        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Получаем список LikedLRoutes
        List<Integer> likedRoutes = user.getLikedRoutesList();
        if (likedRoutes == null) {
            likedRoutes = new ArrayList<>(); // Возможно, список не был инициализирован
        }

        System.out.println("Current liked routes: " + likedRoutes);

        // Логика удаления лайка с маршрута
        if (likedRoutes.contains(routeId)) {
            likedRoutes.remove(routeId);
            user.setLikedRoutesList(likedRoutes);
            if (user.getLikedRoutes() == null || user.getLikedRoutes().trim().isEmpty()) {
                user.setLikedRoutes(", ");
            }
            userService.save(user); // Сохраняем изменения пользователя

            // Уменьшаем параметр likes маршрута
            Route route = routeService.findById(routeId).orElse(null);
            if (route != null) {
                route.setLikes(route.getLikes() - 1);
                routeService.save(route); // Сохраняем изменения маршрута
                System.out.println("Route unliked successfully.");
                return ResponseEntity.ok("Route unliked.");
            } else {
                System.out.println("Route not found!");
                return ResponseEntity.badRequest().body("Route not found.");
            }
        }

        System.out.println("Route was not liked by the user.");
        return ResponseEntity.badRequest().body("Route not liked yet.");
    }

    @GetMapping("/{userId}/like-routes")
    public String userShowRoutesLikedPage(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "404"; // Возвращаем ошибку
        }

        // Получаем список идентификаторов маршрутов, которые пользователь добавил в избранное
        List<Integer> likedRoutesIds = user.getLikedRoutesList();

        // Фильтруем маршруты, чтобы оставить только те, которые есть в избранном у пользователя
        List<Route> likedRoutes = routeService.findAll()
                .stream()
                .filter(route -> likedRoutesIds.contains(route.getId())) // Проверяем наличие id в списке
                .toList();

        model.addAttribute("likedRoutes", likedRoutes);
        model.addAttribute("userId", userId);
        return "likedRoutesPage"; // Страница с избранными маршрутами
    }
}

