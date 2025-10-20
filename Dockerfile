# 1️⃣ Usar imagem base com Java 21
FROM eclipse-temurin:21-jdk-alpine

# 2️⃣ Diretório da aplicação dentro do container
WORKDIR /app

# 3️⃣ Copiar o jar gerado para dentro do container
COPY target/*.jar app.jar

# 4️⃣ Expor a porta que o Spring Boot vai usar
EXPOSE 8080

# 5️⃣ Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]