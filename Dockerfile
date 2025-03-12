# Компилируем и собираем приложение
# Используем базовый образ с OpenJDK
FROM openjdk:17-jdk-slim AS build
# Устанавливаем рабочую директорию
WORKDIR /spb4you-spring
# Копируем ВСЕ файлы проекта в контейнер
COPY . .
# Сборка приложения
RUN ./gradlew build -x test

# Запускаем приложение
# Устанавливаем рабочую директорию для выполнения приложения
FROM openjdk:17-jdk-slim
WORKDIR /spb4you-spring
# Копируем собранный jar файл из предыдущего образа
COPY --from=build /spb4you-spring/build/libs/spb4you-0.0.1-SNAPSHOT.jar spb4you.jar
# Указываем, что приложение слушает на порту 8080
EXPOSE 8080
# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "spb4you.jar"]

