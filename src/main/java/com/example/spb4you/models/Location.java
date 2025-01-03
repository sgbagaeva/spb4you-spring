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
@Table(name = "location")
public class Location {
    @Id
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tag_ids")
    private String tagIds; // Строка для хранения tagIds

    @JsonProperty("description")
    private String description;

    @JsonProperty("geoData")
    private String geoData;

    @JsonProperty("additInfo")
    private String additInfo;

    @JsonProperty("likes")
    private Integer likes;

    @JsonProperty("category_ids")
    private String categoryIds; // Строка для хранения categoryIds

    public Location id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Уникальный идентификатор локации
     * @return id
     */
    @Nonnull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Название локации
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
     * ID тегов, связанных с локацией
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

    public Location description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Описание локации
     * @return description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location geoData(String geoData) {
        this.geoData = geoData;
        return this;
    }

    /**
     * Геоданные локации
     * @return geoData
     */
    public String getGeoData() {
        return geoData;
    }

    public void setGeoData(String geoData) {
        this.geoData = geoData;
    }

    public Location additInfo(String additInfo) {
        this.additInfo = additInfo;
        return this;
    }

    /**
     * Дополнительная информация о локации
     * @return additInfo
     */
    public String getAdditInfo() {
        return additInfo;
    }

    public void setAdditInfo(String additInfo) {
        this.additInfo = additInfo;
    }

    public Location likes(Integer likes) {
        this.likes = likes;
        return this;
    }

    /**
     * Количество лайков локации
     * @return likes
     */
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * ID категорий, связанных с локацией
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
            return Arrays.stream(categoryIds.split(", "))
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
                .collect(Collectors.joining(", ")); // Объединяем в строку с запятыми
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(this.id, location.id) &&
                Objects.equals(this.name, location.name) &&
                Objects.equals(this.tagIds, location.tagIds) &&
                Objects.equals(this.description, location.description) &&
                Objects.equals(this.geoData, location.geoData) &&
                Objects.equals(this.additInfo, location.additInfo) &&
                Objects.equals(this.likes, location.likes) &&
                Objects.equals(this.categoryIds, location.categoryIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tagIds, description, geoData, additInfo, likes, categoryIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Location {\n");
        sb.append(" id: ").append(toIndentedString(id)).append("\n");
        sb.append(" name: ").append(toIndentedString(name)).append("\n");
        sb.append(" tagIds: ").append(toIndentedString(tagIds)).append("\n");
        sb.append(" description: ").append(toIndentedString(description)).append("\n");
        sb.append(" geoData: ").append(toIndentedString(geoData)).append("\n");
        sb.append(" additInfo: ").append(toIndentedString(additInfo)).append("\n");
        sb.append(" likes: ").append(toIndentedString(likes)).append("\n");
        sb.append(" categoryIds: ").append(toIndentedString(categoryIds)).append("\n");
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

