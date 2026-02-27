FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR /app
COPY target/discovery-server-1.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
