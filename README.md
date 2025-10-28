# PRIMO PATO - API

## Ferramentas utilizadas
- Java 21
- Spring Boot 3.5.6
- Maven
- PostgreSQL
- Swagger
- OpenCage
- Junit5
- JPA
- Lombok
- JWT

## Passos para executar a aplicação com Docker
* docker compose -f docker-compose.yml -p primo-pato-api up -d
Para parar
* docker compose -f docker-compose.yml -p primo-pato-api stop

## Passos para executar a aplicação com Maven
### Build API e instalação de dependências
* ./mvnw clean package install

### Rodar API
* ./mvnw spring-boot:run

## Documentação ([Swagger](http://130.107.74.13:8888/swagger-ui/index.html))
