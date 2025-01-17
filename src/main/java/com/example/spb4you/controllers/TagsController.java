package com.example.spb4you.controllers;

import com.example.spb4you.models.Location;
import com.example.spb4you.models.Route;
import com.example.spb4you.models.Tag;
import com.example.spb4you.models.User;
import com.example.spb4you.services.LocationService;
import com.example.spb4you.services.RouteService;
import com.example.spb4you.services.TagService;
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
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private UserService userService;

    @GetMapping("/info/list")
    public ResponseEntity<List<Tag>> listTags() {
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(tags); // Возвращаем список всех тегов со всеми полями для каждой с кодом 200
    }

    @GetMapping("/info/{tagId}")
    public ResponseEntity<Tag> getTagDetails(@PathVariable("tagId") Integer tagId) {
        Tag tag = tagService.findById(tagId).orElse(null);
        assert tag != null;
        return ResponseEntity.ok(tag); // Возвращаем набор полей тега по соответствующему ID с кодом 200
    }

    @GetMapping()
    public String showTags(Model model) {
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        return "tagSearchPage"; // Возвращаем страницу со списком тегов
    }

    @GetMapping("/{tagId}")
    public String showTag(@PathVariable("tagId") Integer tagId, Model model, HttpSession session) {
        // Получаем userId из сессии
        Integer userId = (Integer) session.getAttribute("userId");
        Tag tag = tagService.findById(tagId).orElse(null);

        if (tag != null) {
            model.addAttribute("tag", tag);

            // Получаем пользователя по userId из сессии
            User user = userId != null ? userService.findById(userId).orElse(null) : null;

            // Получаем списки понравившихся
            List<Integer> likedLocationsList = user != null ? user.getLikedLocationsList() : new ArrayList<>();
            List<Integer> likedRoutesList = user != null ? user.getLikedRoutesList() : new ArrayList<>();

            // Инициализируем списки для Liked и Unliked маршрутов и локаций
            List<Route> likedRoutes = new ArrayList<>();
            List<Route> unlikedRoutes = new ArrayList<>();
            List<Location> likedLocations = new ArrayList<>();
            List<Location> unlikedLocations = new ArrayList<>();

            // Фильтруем маршруты по tagId
            List<Route> routes = routeService.findAll()
                    .stream()
                    .filter(route -> route.getTagIdList().stream()
                            .anyMatch(id -> id.equals(tagId))) // Проверка наличия tagId
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

            // Фильтруем локации по tagId
            List<Location> locations = locationService.findAll()
                    .stream()
                    .filter(location -> location.getTagIdList().stream()
                            .anyMatch(id -> id.equals(tagId))) // Проверка наличия tagId
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

            // Добавляем маршруты в модель
            model.addAttribute("likedRoutes", likedRoutes);
            model.addAttribute("unlikedRoutes", unlikedRoutes);

            // Добавляем локации
            model.addAttribute("likedLocations", likedLocations);
            model.addAttribute("unlikedLocations", unlikedLocations);
            return "tagpage"; // Возвращаем имя представления
        }

        return "error"; // Возвращаем страницу ошибки, если тег не найден
    }



}
