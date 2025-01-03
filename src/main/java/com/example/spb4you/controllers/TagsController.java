package com.example.spb4you.controllers;

import com.example.spb4you.models.Location;
import com.example.spb4you.models.Tag;
import com.example.spb4you.services.LocationService;
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

@Controller
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    @Autowired
    private LocationService locationService;

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

            List<Location> locations = locationService.findAll()
                    .stream()
                    .filter(location -> location.getTagIdList().stream()
                            .anyMatch(id -> id.equals(tagId))) // Проверка наличия tagId в списке
                    .toList();

            model.addAttribute("locations", locations);
            model.addAttribute("userId", userId);
            return "tagpage";
        }
        return "error";
    }
}
