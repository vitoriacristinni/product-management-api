# Product Management API

REST API for product management developed with Java and Spring Boot.

This project was created as a backend portfolio project, focusing on RESTful API development, layered architecture, data validation, exception handling, automated testing, API documentation and containerization.

## Technologies

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger / OpenAPI
- JUnit 5
- Mockito

## Features

- Create products
- List all products
- Find a product by ID
- Update products
- Delete products
- Request validation
- Global exception handling
- Automated unit tests
- Controller tests with MockMvc
- API documentation with Swagger/OpenAPI
- PostgreSQL database
- Docker containerization

## Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```
## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/products` | Create a product |
| GET | `/products` | List all products |
| GET | `/products/{id}` | Find a product by ID |
| PUT | `/products/{id}` | Update a product |
| DELETE | `/products/{id}` | Delete a product |

## Running the Application

### Prerequisites

- Java 21
- Maven
- Docker
- Docker Compose

### Using Docker Compose

To start the application and PostgreSQL database:

```bash
docker compose up --build 
```
## Running the Application
### Prerequisites
- Java 21
- Maven
- Docker
- Docker Compose

### Using Docker Compose

To start the application and PostgreSQL database:
```bash
docker compose up --build
```
The API will be available at:

`http://localhost:8080`

To stop the application:
```bash
docker compose down
```
## API Documentation

The API documentation is available through Swagger UI:

`http://localhost:8080/swagger-ui/index.html`

Swagger allows you to visualize and test the API endpoints directly from the browser.

## Testing

To run the automated tests:

```bash
mvn test
```

The project includes:

- Service layer unit tests
- Controller tests with MockMvc 
- Validation tests
- Exception handling tests
- Spring application context test

## Project Goals

This project was developed to practice and demonstrate practical software engineering concepts, including:

- RESTful API development
- Layered architecture
- Separation of responsibilities
- Data validation
- Exception handling
- Automated testing
- API documentation
- Database persistence
- Containerization
- Git version control

## Author

Vitória Cristinni

GitHub: https://github.com/vitoriacristinni