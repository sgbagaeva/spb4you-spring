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
 * Route
 */
@Table(name = "route")
public class Route {
    @Id
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tag_ids")
    private String tagIds; // Строка для хранения tagIds

    @JsonProperty("description")
    private String description;

    @JsonProperty("distance")
    private Double distance; // Double для хранения расстояния

    @JsonProperty("time")
    private String time; // String для времени

    @JsonProperty("steps")
    private String steps; // Для хранения шагов маршрута

    @JsonProperty("likes")
    private Integer likes;

    @JsonProperty("category_ids")
    private String categoryIds; // Строка для хранения categoryIds

    public Route id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Уникальный идентификатор маршрута
     * @return id
     */
    @Nonnull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Название маршрута
     * @return name
     */
    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ID тегов, связанных с маршрутом
     * @return tagIds
     */
    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<Integer> getTagIdList() {
        if (tagIds != null && !tagIds.isEmpty()) {
            return Arrays.stream(tagIds.split(", "))
                    .map(String::trim) // Удаляем возможные пробелы
                    .map(Integer::valueOf) // Преобразуем в Integer
                    .collect(Collectors.toList());
        } else {
            return List.of(); // Возвращаем пустой список если строка пустая
        }
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIds = tagIdList.stream()
                .map(String::valueOf) // Преобразуем Integer в String
                .collect(Collectors.joining(", ")); // Объединяем в строку с запятыми
    }

    public Route description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Описание маршрута
     * @return description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Route distance(Double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Расстояние маршрута
     * @return distance
     */
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Route time(String time) {
        this.time = time;
        return this;
    }

    /**
     * Время маршрута
     * @return time
     */
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Route steps(String steps) {
        this.steps = steps;
        return this;
    }

    /**
     * Шаги маршрута
     * @return steps
     */
    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Route likes(Integer likes) {
        this.likes = likes;
        return this;
    }

    /**
     * Количество лайков маршрута
     * @return likes
     */
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * ID категорий, связанных с маршрутом
     * @return categoryIds
     */
    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Integer> getCategoryIdList() {
        if (categoryIds != null && !categoryIds.isEmpty()) {
            return Arrays.stream(categoryIds.split(","))
                    .map(String::trim) // Удаляем возможные пробелы
                    .map(Integer::valueOf) // Преобразуем в Integer
                    .collect(Collectors.toList());
        } else {
            return List.of(); // Возвращаем пустой список если строка пустая
        }
    }

    public void setCategoryIdList(List<Integer> categoryIdList) {
        this.categoryIds = categoryIdList.stream()
                .map(String::valueOf) // Преобразуем Integer в String
                .collect(Collectors.joining(",")); // Объединяем в строку с запятыми
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        return Objects.equals(this.id, route.id) &&
                Objects.equals(this.name, route.name) &&
                Objects.equals(this.tagIds, route.tagIds) &&
                Objects.equals(this.description, route.description) &&
                Objects.equals(this.distance, route.distance) &&
                Objects.equals(this.time, route.time) &&
                Objects.equals(this.steps, route.steps) &&
                Objects.equals(this.likes, route.likes) &&
                Objects.equals(this.categoryIds, route.categoryIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tagIds, description, distance, time, steps, likes, categoryIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Route {\n");
        sb.append(" id: ").append(toIndentedString(id)).append("\n");
        sb.append(" name: ").append(toIndentedString(name)).append("\n");
        sb.append(" tagIds: ").append(toIndentedString(tagIds)).append("\n");
        sb.append(" description: ").append(toIndentedString(description)).append("\n");
        sb.append(" distance: ").append(toIndentedString(distance)).append("\n");
        sb.append(" time: ").append(toIndentedString(time)).append("\n");
        sb.append(" steps: ").append(toIndentedString(steps)).append("\n");
        sb.append(" likes: ").append(toIndentedString(likes)).append("\n");
        sb.append(" categoryIds: ").append(toIndentedString(categoryIds)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n ");
    }
}
