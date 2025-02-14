package com.example.spb4you.controllers;

import com.example.spb4you.models.Location;
import com.example.spb4you.services.LocationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/locations")
public class LocationsController {

    @Autowired
    LocationService locationService;

    @GetMapping("/info/list")
    public ResponseEntity<List<Location>> listLocations() {
        List<Location> locations = locationService.findAll();
        return ResponseEntity.ok(locations); // Возвращаем список всех локаций со всеми полями для каждой с кодом 200
    }

    @GetMapping("/info/{locationId}")
    public ResponseEntity<Location> getLocationDetails(@PathVariable("locationId") Integer locationId) {
        Location location = locationService.findById(locationId).orElse(null);
        assert location != null;
        return ResponseEntity.ok(location); // Возвращаем набор полей локации по соответствующему ID с кодом 200
    }

    @GetMapping("/{locationId}")
    public String getLocation(@PathVariable("locationId") Integer locationId, HttpSession session, Model model) {
        Location location = locationService.findById(locationId).orElse(null);
        if (location != null) {
            model.addAttribute("location", location);
            session.setAttribute("locationId", locationId);
            return "location"; // Возвращаем HTML-страницу соответствующей локации
        }
        return "404"; // Если такой локации не существует - возвращаем страницу ошибки
    }

    @GetMapping("/map")
    public String getLocationMap(HttpSession session, Model model) {
        // Извлекаем id локации из сессии
        Integer locationId = (Integer) session.getAttribute("locationId");
        if (locationId == null) {
            // Перенаправление на страницу 404, когда locationId отсутствует в сессии
            return "redirect:/404";
            // Кстати, нужно сделать страницы ошибок - у нас их сейчас нет
        }

        Location location = locationService.findById(locationId).orElse(null);
        if (location == null) {
            // еренаправление на страницу 404, когда локация не найдена
            return "redirect:/404";
        }

        /* Тут предполагаем, что координаты хранятся в строковом формате.
        Здесь нужно изменить логику получения массива координат в соответствие с тем,
        как у нас хранятся эти данные в Mongo.
        */
        String coordinates = location.getGeoData(); // Получаем строку с координатами
        String[] coordinateParts = coordinates.trim().split(", ");
        double[] locationCoordinates;

        try {
            // Парсим координаты
            locationCoordinates = new double[2];
            locationCoordinates[0] = Double.parseDouble(coordinateParts[0]); // Широта
            locationCoordinates[1] = Double.parseDouble(coordinateParts[1]); // Долгота
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Обработка ошибок парсинга координат
            return "redirect:/404"; // или перенаправление на страницу с ошибкой
        }

        // Добавляем координаты и ID локации в модель
        model.addAttribute("locationId", location.getId());
        model.addAttribute("locationCoordinates", locationCoordinates);

        return "mapLocation"; // имя вашего HTML файла
    }

    @GetMapping("/info/listbytag/{tagId}")
    public ResponseEntity<List<Location>> listLocationsByTag(@PathVariable("tagId") Integer tagId) {
        List<Location> locations = locationService.findAll()
                .stream()
                .filter(location -> location.getTagIdList().stream()
                        .anyMatch(id -> id.equals(tagId))) // Проверка наличия tagId в списке
                .toList();
        return ResponseEntity.ok(locations); // Возвращаем список локаций по соответствующему тегу
    }

    @GetMapping("/update/{locationId}")
    public String updateLocation(@PathVariable Integer locationId, HttpSession session, Model model) {
        Location location = locationService.findById(locationId).orElse(null);
        assert location != null;
        model.addAttribute("location", location);
        session.setAttribute("locationId", locationId);
        return "update-location"; // Название шаблона для редактирования
    }

//    @PostMapping("/update")
//    public String updateLocation(@ModelAttribute Location location) {
//        locationService.save(location);
//        return "redirect:/locations"; // Вернуться к списку локаций
//    }

    @GetMapping("/delete/{locationId}")
    public String deleteLocation(@PathVariable Integer locationId, Model model) {
        Location location = locationService.findById(locationId).orElse(null);
        model.addAttribute("location", location);
        return "nots/del_location_ask";
    }

//    @GetMapping("/delete/{locationId}")
//    public String deleteLocation(@PathVariable Integer locationId) {
//        // locationService.deleteById(locationId);
//        return "nots/del_location_success"; // Вернуться к списку локаций после удаления
//    }
}
