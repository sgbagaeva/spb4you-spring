package com.example.spb4you.controllers;

import com.example.spb4you.models.Location;
import com.example.spb4you.models.Route;
import com.example.spb4you.models.User;
import com.example.spb4you.services.LocationService;
import com.example.spb4you.services.RouteService;
import com.example.spb4you.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

    @GetMapping("/info/list")
    public ResponseEntity<List<User>> listAdmins() {
        List<User> users = userService.findAll()
                .stream()
                .filter(user -> "Администратор".equals(user.getRole()))
                .toList();
        return ResponseEntity.ok(users); // Возвращаем список всех администраторов со всеми полями для каждого с кодом 200
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<User> getAdminDetails(@PathVariable("userId") Integer userId) {
        // Находим пользователя по ID
        User user = userService.findById(userId).orElse(null);

        // Проверяем, что пользователь найден и имеет роль "user"
        if (user != null && "Администратор".equals(user.getRole())) {
            return ResponseEntity.ok(user); // Возвращаем набор полей администратора с кодом 200
        }

        // Если пользователь не найден или не имеет правильную роль, возвращаем статус 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Возвращаем 404 Not Found
    }


    @GetMapping("/index/{userId}")
    public String userInfo(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            // Обработка случая, если пользователь не найден
            return "redirect:/error"; // Возвращаем ошибку
        }
        model.addAttribute("user", user);
        return "adminIndex"; // Главная страница администратора
    }

    @GetMapping("/manage-locs-routes")
    public String manageLocationsAndRoutes() {
        return "admin-pages/manage-locs-routes"; // возвращаем имя шаблона
    }

    @GetMapping("/manage-locations")
    public String manageLocations(Model model) {
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        return "admin-pages/manage-locs"; // возвращаем имя шаблона
    }

    @GetMapping("/manage-routes")
    public String manageRoutes(Model model) {
        List<Route> routes = routeService.findAll();
        model.addAttribute("routes", routes);
        return "admin-pages/manage-routes"; // возвращаем имя шаблона
    }

    @GetMapping("/manage-locations/{locationId}")
    public String manageLocation(@PathVariable("locationId") Integer locationId, Model model) {
        Location location = locationService.findById(locationId).orElse(null);
        assert location != null;
        model.addAttribute("location", location);
        return "admin-pages/manage-loc"; // возвращаем имя шаблона
    }

}


