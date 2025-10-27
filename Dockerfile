FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

RUN ./mvnw clean package -DskipTests

COPY . .

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
