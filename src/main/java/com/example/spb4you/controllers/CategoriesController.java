package com.example.spb4you.controllers;

import com.example.spb4you.models.Category;
import com.example.spb4you.models.Location;
import com.example.spb4you.models.Route;
import com.example.spb4you.models.Tag;
import com.example.spb4you.services.CategoryService;
import com.example.spb4you.services.LocationService;
import com.example.spb4you.services.RouteService;
import com.example.spb4you.services.TagService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

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
    public String showLocationsCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);

            List<Location> locations = locationService.findAll()
                    .stream()
                    .filter(location -> location.getCategoryIdList().stream()
                            .anyMatch(id -> id.equals(categoryId))) // Проверка наличия categoryId в списке
                    .toList();

            model.addAttribute("locations", locations);
            return "categorypageLoc";
        }
        return "error";
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
    public String showRoutesCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);

            List<Route> routes = routeService.findAll()
                    .stream()
                    .filter(route -> route.getCategoryIdList().stream()
                            .anyMatch(id -> id.equals(categoryId))) // Проверка наличия categoryId в списке
                    .toList();

            model.addAttribute("routes", routes);
            return "categorypageRoutes";
        }
        return "error";
    }
}
