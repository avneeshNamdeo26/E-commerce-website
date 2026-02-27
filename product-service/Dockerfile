FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR /app
COPY target/product-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
