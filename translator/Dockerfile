# syntax=docker/dockerfile:experimental

#
# Build stage
#
FROM openjdk:11-jdk-slim-buster AS build
WORKDIR /workspace/app

COPY src /workspace/app/src
COPY pom.xml /workspace/app
COPY mvnw /workspace/app
COPY .mvn /workspace/app/.mvn

RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests


#
# Package stage
#
FROM openjdk:11-jdk-slim-buster
VOLUME /tmp
COPY --from=build /workspace/app/target/translator.jar /root/app.jar
ENTRYPOINT ["java", "-jar", "/root/app.jar"]
#ENTRYPOINT ["java", "-jar", "/root/app.jar","--spring.profiles.active=local","--server.port=8666"]
