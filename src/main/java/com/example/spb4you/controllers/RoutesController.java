package com.example.spb4you.controllers;

import com.example.spb4you.models.Point;
import com.example.spb4you.models.Route;
import com.example.spb4you.services.PointService;
import com.example.spb4you.services.RouteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Controller
@RequestMapping("/routes")
public class RoutesController {

    @Autowired
    RouteService routeService;

    @Autowired
    PointService pointService;

    @GetMapping("/info/list")
    public ResponseEntity<List<Route>> listRoutes() {
        List<Route> routes = routeService.findAll();
        return ResponseEntity.ok(routes); // Возвращаем список всех маршрутов со всеми полями для каждого с кодом 200
    }

    @GetMapping("/info/{routeId}")
    public ResponseEntity<Route> getRouteDetails(@PathVariable("routeId") Integer locationId) {
        Route route = routeService.findById(locationId).orElse(null);
        assert route != null;
        return ResponseEntity.ok(route); // Возвращаем набор полей маршрута по соответствующему ID с кодом 200
    }

    @GetMapping("/{routeId}")
    public String getRouteDetails(@PathVariable("routeId") Integer routeId, HttpSession session, Model model) {
        Route route = routeService.findById(routeId).orElse(null);
        if (route != null) {
            model.addAttribute("route", route);
            session.setAttribute("routeId", routeId);
            return "route"; // Имя шаблона Thymeleaf
        }
        return "404";
    }

    @GetMapping("/map")
    public String getRouteMap(HttpSession session, Model model) {
        // Извлекаем id маршрута из сессии
        Integer routeId = (Integer) session.getAttribute("routeId");
        if (routeId == null) {
            // Обработка случая, когда routeId отсутствует в сессии
            return "redirect:/404"; // или перенаправление на другую страницу
        }

        Route route = routeService.findById(routeId).orElse(null);
        if (route == null) {
            // Обработка случая, когда маршрут не найдена
            return "redirect:/404"; // или перенаправление на другую страницу
        }

        Map<String, List<Double>> pointsMap = pointService.findAll()
                .stream()
                .filter(point -> routeId.equals(point.getRouteId()))
                .sorted(Comparator.comparingLong(Point::getId)) // Сортировка по `point.getId()`
                .collect(Collectors.toMap(
                        point -> Optional.ofNullable(point.getName()).orElse("no" + point.getId()),
                        Point::getCoordinatesList,
                        (existing, replacement) -> existing, // Обработка коллизий, если ключи совпадают
                        LinkedHashMap::new // Использование LinkedHashMap для сохранения порядка вставки
                ));


        // Добавляем ID маршрута и словарь точек в модель
        model.addAttribute("routeId", route.getId());
        model.addAttribute("points", pointsMap);

        return "mapRoute"; // имя вашего HTML файла
    }

    @GetMapping("/info/listbytag/{tagId}")
    public ResponseEntity<List<Route>> listRoutesByTag(@PathVariable("tagId") Integer tagId) {
        List<Route> routes = routeService.findAll()
                .stream()
                .filter(route -> route.getTagIdList().stream()
                        .anyMatch(id -> id.equals(tagId))) // Проверка наличия tagId в списке
                .toList();
        return ResponseEntity.ok(routes); // Возвращаем список локаций по соответствующему тегу
    }
}
