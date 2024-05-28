FROM amazoncorretto:22.0.1-al2023
ARG JAR_FILE=target/*.jar
COPY ./target/URLShortener-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080