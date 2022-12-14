# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /home/app
COPY . /home/app
RUN chmod 777 /home/app/gradlew
RUN ./gradlew clean build

FROM eclipse-temurin:17-jdk-jammy


COPY --from=build /home/app/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
