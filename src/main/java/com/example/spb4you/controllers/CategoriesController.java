package com.example.spb4you.controllers;

import com.example.spb4you.models.*;
import com.example.spb4you.services.CategoryService;
import com.example.spb4you.services.LocationService;
import com.example.spb4you.services.RouteService;
import com.example.spb4you.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private UserService userService;

    @GetMapping("/info/list")
    public ResponseEntity<List<Category>> listCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories); // Возвращаем список всех категорий со всеми полями для каждой с кодом 200
    }

    @GetMapping("/info/{categoryId}")
    public ResponseEntity<Category> getTagDetails(@PathVariable("categoryId") Integer categoryId) {
        Category category = categoryService.findById(categoryId).orElse(null);
        assert category != null;
        return ResponseEntity.ok(category); // Возвращаем набор полей категории по соответствующему ID с кодом 200
    }

    @GetMapping("/locations")
    public  String showCategoryChoiceLocation(Model model) {
        List<Category> categories = categoryService.findAll()
                .stream()
                .filter(category -> "Локации".equals(category.getType()))
                .toList();
        model.addAttribute("categories", categories);
        return "categoryChoiceLoc";
    }

    @GetMapping("/locations/{categoryId}")
    public String showLocationsCategory(@PathVariable("categoryId") Integer categoryId, Model model, HttpSession session) {
        // Получаем userId из сессии
        Integer userId = (Integer) session.getAttribute("userId");
        Category category = categoryService.findById(categoryId).orElse(null);

        if (category != null) {
            model.addAttribute("category", category);

            // Получаем пользователя по userId из сессии
            User user = userId != null ? userService.findById(userId).orElse(null) : null;
            List<Integer> likedLocationsList = user != null ? user.getLikedLocationsList() : new ArrayList<>();

            // Инициализируем списки для Liked и Unliked локаций
            List<Location> likedLocations = new ArrayList<>();
            List<Location> unlikedLocations = new ArrayList<>();

            // Фильтруем локации по categoryId
            List<Location> locations = locationService.findAll()
                    .stream()
                    .filter(location -> location.getCategoryIdList().stream()
                            .anyMatch(id -> id.equals(categoryId))) // Проверка наличия categoryId в списке
                    .toList();

            // Если userId не найден, добавляем все локации в unlikedLocations
            if (userId == null) {
                unlikedLocations.addAll(locations);
            } else {
                // Разделяем локации на понравившиеся и непонравившиеся
                for (Location location : locations) {
                    if (likedLocationsList.contains(location.getId())) {
                        likedLocations.add(location); // Добавляем в понравившиеся
                    } else {
                        unlikedLocations.add(location); // Добавляем в непонравившиеся
                    }
                }
            }

            // Добавляем оба списка в модель
            model.addAttribute("likedLocations", likedLocations);
            model.addAttribute("unlikedLocations", unlikedLocations);
            return "categorypageLocs"; // Возвращаем имя представления
        }

        return "error"; // Возвращаем страницу ошибки, если категория не найдена
    }


    @GetMapping("/routes")
    public  String showCategoryChoiceRoute(Model model) {
        List<Category> categories = categoryService.findAll()
                        .stream()
                        .filter(category -> "Маршруты".equals(category.getType()))
                        .toList();
        model.addAttribute("categories", categories);
        return "categoryChoiceRoute";
    }

    @GetMapping("/routes/{categoryId}")
    public String showRoutesCategory(@PathVariable("categoryId") Integer categoryId, Model model, HttpSession session) {
        // Получаем userId из сессии
        Integer userId = (Integer) session.getAttribute("userId");
        Category category = categoryService.findById(categoryId).orElse(null);

        if (category != null) {
            model.addAttribute("category", category);

            // Получаем пользователя по userId из сессии
            User user = userId != null ? userService.findById(userId).orElse(null) : null;
            List<Integer> likedRoutesList = user != null ? user.getLikedRoutesList() : new ArrayList<>();

            // Инициализируем списки для понравившихся и непонравившихся маршрутов
            List<Route> likedRoutes = new ArrayList<>();
            List<Route> unlikedRoutes = new ArrayList<>();

            // Фильтруем маршруты по categoryId
            List<Route> routes = routeService.findAll()
                    .stream()
                    .filter(route -> route.getCategoryIdList().stream()
                            .anyMatch(id -> id.equals(categoryId))) // Проверка наличия categoryId в списке
                    .toList();

            // Если userId не найден, добавляем все маршруты в unlikedRoutes
            if (userId == null) {
                unlikedRoutes.addAll(routes);
            } else {
                // Разделяем маршруты на понравившиеся и непонравившиеся
                for (Route route : routes) {
                    if (likedRoutesList.contains(route.getId())) {
                        likedRoutes.add(route); // Добавляем в понравившиеся
                    } else {
                        unlikedRoutes.add(route); // Добавляем в непонравившиеся
                    }
                }
            }

            // Добавляем оба списка в модель
            model.addAttribute("likedRoutes", likedRoutes);
            model.addAttribute("unlikedRoutes", unlikedRoutes);
            return "categorypageRoutes"; // Указываем имя шаблона для отображения
        }

        return "error"; // Возврат ошибки, если категория не найдена
    }


}
