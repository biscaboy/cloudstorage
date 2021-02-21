# Pre-spring-boot 3.2 Dockerfile
# Source: https://reflectoring.io/spring-boot-docker/
FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]