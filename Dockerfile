FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY target/*.jar app.jar

COPY keystore.p12 /app/keystore.p12

EXPOSE 8443

ENTRYPOINT ["java", "-jar", "app.jar"]
