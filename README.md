# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## Technologies
- **Java 21 LTS**
- Spring Boot 3.2.x
- Spring Web
- Spring Data JPA
- H2 database
- Maven

## Como executar
1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse: http://localhost:8000/health

## Endpoints
- GET /health - Status da aplicação (inclui versão Java)
- GET /info - Informações da aplicação
- GET /h2-console - Console do banco H2

## Configuração
- Porta: 8080
- Banco: H2 em memória
- Profile: development

## Desenvolvedor
Claudio - Ciência da Computação