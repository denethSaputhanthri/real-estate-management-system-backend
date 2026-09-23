# Real Estate Management System — Backend

Backend REST API for a real estate management platform built with **Spring Boot** and **PostgreSQL**. The project follows a layered architecture and provides a secure, scalable foundation for managing users and real estate operations.

## 🚀 Features

* User registration and management
* Role-based user system
* User status management
* RESTful API architecture
* PostgreSQL database integration
* Database version control with Flyway
* Spring Data JPA for persistence
* Spring Security integration
* Request validation
* Centralized application configuration
* API documentation with OpenAPI / Swagger
* Layered backend architecture
* Environment-based database configuration

## 🛠️ Technologies

| Technology         | Purpose                            |
| ------------------ | ---------------------------------- |
| Java 25            | Backend programming language       |
| Spring Boot 4.1.1  | Application framework              |
| Spring Web         | REST API development               |
| Spring Data JPA    | Database persistence               |
| PostgreSQL         | Relational database                |
| Flyway             | Database migration                 |
| Spring Security    | Authentication & authorization     |
| Jakarta Validation | Request validation                 |
| Lombok             | Boilerplate reduction              |
| SpringDoc OpenAPI  | API documentation                  |
| Maven              | Dependency management & build      |
| Docker             | PostgreSQL development environment |

## 🏗️ Architecture

The project follows a layered architecture to keep business logic, API handling, and data access separated.

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

### Main Layers

```text
src/main/java/com/realestate
│
├── config
│   └── Application and security configuration
│
├── controller
│   └── REST API endpoints
│
├── dto
│   └── Request and response objects
│
├── model
│   └── JPA entities
│
├── repository
│   └── Database access layer
│
├── service
│   └── Business logic
│
└── util
    └── Utility classes
```

## 🗄️ Database

The application uses **PostgreSQL** as its primary database.

### Database

```text
Database Name: realestate_db
Port: 5432
```

### User Roles

The system currently supports the following roles:

```text
CUSTOMER
SELLER
AGENT
ADMIN
```

### User Status

```text
ACTIVE
DISABLED
```

### Users Table

The initial database migration creates a `users` table containing:

```text
id
name
email
password
phone
role
status
created_at
updated_at
```

Database schema changes are managed through **Flyway migrations**.

## 🔄 Database Migration

Flyway manages database versioning.

Migration files are located under:

```text
src/main/resources/db/migration
```

Example:

```text
V1__create_users_table.sql
```

Whenever a new database change is required, create a new migration instead of manually modifying an existing migration.

Example:

```text
V2__create_properties_table.sql
V3__create_property_images_table.sql
V4__create_property_features_table.sql
```

## ⚙️ Configuration

The application uses environment variables for sensitive configuration values.

Example:

```env
DB_URL=jdbc:postgresql://localhost:5432/realestate_db
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

> Do not commit `.env` files or database passwords to GitHub.

Example Spring configuration:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: validate

  flyway:
    enabled: true
```

## 🐘 Running PostgreSQL with Docker

PostgreSQL can be run using Docker.

Example:

```bash
docker run --name realestate-postgres \
  -e POSTGRES_DB=realestate_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=your_password \
  -p 5432:5432 \
  -d postgres
```

Check the container:

```bash
docker ps
```

Stop the container:

```bash
docker stop realestate-postgres
```

Start it again:

```bash
docker start realestate-postgres
```

## 📋 Prerequisites

Make sure the following are installed:

* Java 25
* Maven
* PostgreSQL or Docker
* Git
* IntelliJ IDEA or another Java IDE

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

## 📥 Installation

Clone the repository:

```bash
git clone https://github.com/denethSaputhanthri/real-estate-management-system-backend.git
```

Navigate into the project:

```bash
cd real-estate-management-system-backend
```

Configure the required environment variables:

```env
DB_URL=jdbc:postgresql://localhost:5432/realestate_db
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

Start PostgreSQL and run the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## 📖 API Documentation

The project uses **SpringDoc OpenAPI** for API documentation.

After starting the application, Swagger UI can be accessed at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI documentation:

```text
http://localhost:8080/v3/api-docs
```

## 🔐 Security

Spring Security is integrated into the backend to provide a foundation for:

* Authentication
* Authorization
* Role-based access control
* Protected REST endpoints
* Secure password handling

The security layer is designed to support different access levels for:

```text
CUSTOMER
SELLER
AGENT
ADMIN
```

## 🧪 Testing

Run the test suite with:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

## 📂 Project Structure

```text
real-estate-management-system-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── realestate/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           └── util/
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       └── application.yml
│   │
│   └── test/
│
├── .gitignore
├── pom.xml
└── README.md
```

## 🔮 Future Development

Planned modules include:

* Property management
* Property listing management
* Property search and filtering
* Property image management
* Seller management
* Agent management
* Customer management
* Property inquiries
* Favorites / saved properties
* Property viewing appointments
* Property status management
* Advanced search
* JWT-based authentication
* Role-based API authorization
* Global exception handling
* Pagination and sorting
* API testing
* Production deployment

## 🎯 Project Goals

The main goal of this project is to develop a scalable backend architecture for a modern real estate management platform while applying industry-standard backend development practices.

The project focuses on:

* Clean architecture
* Maintainable code
* Secure API development
* Database version control
* RESTful API design
* Separation of concerns
* Scalable business logic

## 👨‍💻 Author

**Deneth Saputhanthri**

Software Engineering Undergraduate
Colombo, Sri Lanka

GitHub:
https://github.com/denethSaputhanthri

## 📄 License

This project is developed for learning, portfolio, and software engineering practice purposes.
