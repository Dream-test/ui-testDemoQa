# 1. Сборка зависимостей и компиляция кода
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B #Загрузка зависимостей
COPY src ./src
RUN mvn package -DskipTests  #Компиляция кода без запуска тестов

# 2, Контейнер для тестирования
FROM eclipse-temurin:23-jdk
# FROM maven:3.9.9-eclipse-temurin-23  # Использую тот же образ Maven
WORKDIR /app
COPY --from=build /app/target/ui-testL76-1.0-SNAPSHOT.jar /app/ui-testL76.jar
RUN apt-get update && apt-get install -y maven
COPY pom.xml .
COPY src /app/src
CMD ["mvn", "clean", "test"]