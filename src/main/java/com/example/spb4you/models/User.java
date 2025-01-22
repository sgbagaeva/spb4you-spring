package com.example.spb4you.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User
 */
@Table(name = "user")
public class User {
    @Id
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username; // Имя пользователя в системе

    @JsonProperty("first_name")
    private String firstName; // Имя пользователя

    @JsonProperty("last_name")
    private String lastName; // Фамилия пользователя

    @JsonProperty("email")
    private String email; // Электронная почта пользователя

    @JsonProperty("password")
    private String password; // Пароль пользователя

    @JsonProperty("role")
    private String role; // Роль пользователя

    @JsonProperty("registration_date")
    @DateTimeFormat
    private LocalDateTime registrationDate; // Дата регистрации пользователя

    @JsonProperty("avatar_path")
    private String avatarPath; // Путь к аватару пользователя

    @JsonProperty("liked_locations")
    private String likedLocations; // Строка для хранения IDs понравившихся категорий

    @JsonProperty("liked_routes")
    private String likedRoutes; // Строка для хранения IDs понравившихся локаций

    public User id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Уникальный идентификатор пользователя
     * @return id
     */
    @Nonnull
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Имя пользователя в системе
     * @return username
     */
    @Nonnull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Имя пользователя
     * @return first_name
     */
    @Nonnull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Фамилия пользователя
     * @return last_name
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Электронная почта пользователя
     * @return email
     */
    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Пароль пользователя
     * @return password
     */
    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User role(String role) {
        this.role = role;
        return this;
    }

    /**
     * Роль пользователя
     * @return role
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User registrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    /**
     * Дата регистрации пользователя
     * @return registrationDate
     */
    @Nonnull
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Путь к аватару пользователя
     * @return avatarPath
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    /**
     * ID понравившихся локаций
     * @return likedLocations
     */
    public String getLikedLocations() {
        return likedLocations;
    }

    public void setLikedLocations(String likedLocations) {
        this.likedLocations = likedLocations;
    }

    public List<Integer> getLikedLocationsList() {
        if (likedLocations != null && !likedLocations.trim().isEmpty() && !likedLocations.trim().equals(", ")) {
            return Arrays.stream(likedLocations.split(", "))
                    .map(String::trim) // Удаляем возможные пробелы
                    .map(Integer::valueOf) // Преобразуем в Integer
                    .collect(Collectors.toList());
        } else {
            return List.of(); // Возвращаем пустой список если строка пустая
        }
    }

    public void setLikedLocationsList(List<Integer> locationIdList) {
        this.likedLocations = locationIdList.stream()
                .map(String::valueOf) // Преобразуем Integer в String
                .collect(Collectors.joining(", ")); // Объединяем в строку с запятыми
    }

    /**
     * ID понравившихся маршрутов
     * @return likedRoutes
     */
    public String getLikedRoutes() {
        return likedRoutes;
    }

    public void setLikedRoutes(String likedRoutes) {
        this.likedRoutes = likedRoutes;
    }

    public List<Integer> getLikedRoutesList() {
        if (likedRoutes != null && !likedRoutes.isEmpty() && !likedRoutes.trim().equals(", ")) {
            return Arrays.stream(likedRoutes.split(", "))
                    .map(String::trim) // Удаляем возможные пробелы
                    .map(Integer::valueOf) // Преобразуем в Integer
                    .collect(Collectors.toList());
        } else {
            return List.of(); // Возвращаем пустой список если строка пустая
        }
    }

    public void setLikedRoutesList(List<Integer> routeIdList) {
        this.likedRoutes = routeIdList.stream()
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
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.username, user.username) &&
                Objects.equals(this.firstName, user.firstName) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.role, user.role) &&
                Objects.equals(this.registrationDate, user.registrationDate) &&
                Objects.equals(this.avatarPath, user.avatarPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, password, role, registrationDate, avatarPath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");
        sb.append(" id: ").append(toIndentedString(id)).append("\n");
        sb.append(" username: ").append(toIndentedString(username)).append("\n");
        sb.append(" first_name: ").append(toIndentedString(firstName)).append("\n");
        sb.append(" last_name: ").append(toIndentedString(lastName)).append("\n");
        sb.append(" email: ").append(toIndentedString(email)).append("\n");
        sb.append(" password: ").append(toIndentedString(password)).append("\n");
        sb.append(" role: ").append(toIndentedString(role)).append("\n");
        sb.append(" registration_date: ").append(toIndentedString(registrationDate)).append("\n");
        sb.append(" avatar_path: ").append(toIndentedString(avatarPath)).append("\n");
        sb.append(" liked_locations: ").append(toIndentedString(likedLocations)).append("\n");
        sb.append(" liked_routes: ").append(toIndentedString(likedRoutes)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n ");
    }
}
