# Используем образ Gradle для сборки
FROM gradle:7.4.2-jdk17 AS build

# Устанавливаем рабочую директорию
WORKDIR /spb4you-spring

# Копируем все файлы проекта в контейнер
COPY . .

# Устанавливаем права на выполнение для gradlew
RUN chmod +x gradlew

# Сборка приложения
RUN ./gradlew build -x test

# Запускаем приложение
FROM openjdk:17-jdk-slim

WORKDIR /spb4you-spring

# Копируем собранный jar файл из предыдущего образа
COPY --from=build /spb4you-spring/build/libs/spb4you-0.0.1-SNAPSHOT.jar spb4you.jar

# Указываем, что приложение слушает на порту 8080
EXPOSE 8080

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "spb4you.jar"]


