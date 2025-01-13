package com.example.spb4you.controllers;

import com.example.spb4you.models.Location;
import com.example.spb4you.models.Tag;
import com.example.spb4you.models.User;
import com.example.spb4you.services.LocationService;
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
        Integer userId = (Integer) session.getAttribute("userId");
        Tag tag = tagService.findById(tagId).orElse(null);

        if (tag != null) {
            model.addAttribute("tag", tag);

            // Получаем пользователя по userId из сессии
            User user = userService.findById(userId).orElse(null);
            List<Integer> likedLocationsList = user != null ? user.getLikedLocationsList() : new ArrayList<>();

            // Инициализируем списки дляLiked и Unliked локаций
            List<Location> likedLocations = new ArrayList<>();
            List<Location> unlikedLocations = new ArrayList<>();

            // Фильтруем локации по tagId
            List<Location> locations = locationService.findAll()
                    .stream()
                    .filter(location -> location.getTagIdList().stream()
                            .anyMatch(id -> id.equals(tagId))) // Проверка наличия tagId
                    .toList();

            // Разделяем локации на понравившиеся и непонравившиеся
            for (Location location : locations) {
                if (likedLocationsList.contains(location.getId())) {
                    likedLocations.add(location); // Добавляем в понравившиеся
                } else {
                    unlikedLocations.add(location); // Добавляем в непонравившиеся
                }
            }

            // Добавляем оба списка в модель
            model.addAttribute("likedLocations", likedLocations);
            model.addAttribute("unlikedLocations", unlikedLocations);
            model.addAttribute("userId", userId);
            return "tagpage"; // Возвращаем имя представления
        }

        return "error"; // Возвращаем страницу ошибки, если тег не найден
    }

}
