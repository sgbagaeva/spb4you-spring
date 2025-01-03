package com.example.spb4you.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Location
 */
@Table(name = "point")
public class Point {
    @Id
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("route_id")
    private Integer routeId;

    @JsonProperty("coordinates")
    private String coordinates;

    @JsonProperty("name")
    private String name;

    @JsonProperty("adress")
    private String adress;

    public Point id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Уникальный идентификатор точки
     * @return id
     */
    @Nonnull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Point routeId(Integer routeId) {
        this.routeId = routeId;
        return this;
    }

    /**
     * ID маршрута
     * @return name
     */
    @Nonnull
    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    /**
     * Координаты точки
     * @return coordinates
     */
    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<Double> getCoordinatesList() {
        if (coordinates != null && !coordinates.isEmpty()) {
            return Arrays.stream(coordinates.split(", "))
                    .map(String::trim) // Удаляем возможные пробелы
                    .map(Double::valueOf) // Преобразуем в Integer
                    .collect(Collectors.toList());
        } else {
            return List.of(); // Возвращаем пустой список если строка пустая
        }
    }

    public void setCoordinatesList(List<Double> coordinatesList) {
        this.coordinates = coordinatesList.stream()
                .map(String::valueOf) // Преобразуем Integer в String
                .collect(Collectors.joining(", ")); // Объединяем в строку с запятыми
    }

    public Point name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Название точки
     * @return name
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point adress(String adress) {
        this.adress = adress;
        return this;
    }

    /**
     * Адрес точки
     * @return adress
     */

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Objects.equals(this.id, point.id) &&
                Objects.equals(this.routeId, point.routeId) &&
                Objects.equals(this.coordinates, point.coordinates)&&
                Objects.equals(this.name, point.name)&&
                Objects.equals(this.adress, point.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, routeId, coordinates, name, adress);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Location {\n");
        sb.append(" id: ").append(toIndentedString(id)).append("\n");
        sb.append(" routeId: ").append(toIndentedString(routeId)).append("\n");
        sb.append(" coordinates: ").append(toIndentedString(coordinates)).append("\n");
        sb.append(" name: ").append(toIndentedString(name)).append("\n");
        sb.append(" adress: ").append(toIndentedString(adress)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n ");
    }
}

