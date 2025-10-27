# Etapa 1: Build
FROM maven:3.9.3-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Build do JAR sem testes
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Porta da aplicação
EXPOSE 8888

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
