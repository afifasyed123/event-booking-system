# Event Booking System

A backend REST API for managing events, users, and ticket bookings, built with **Java and Spring Boot**.

The application provides secure user authentication using **JWT**, role-based authorization, event management, ticket booking, validation, exception handling, pagination, sorting, API documentation, automated testing, Docker containerization, and cloud deployment.

## 🚀 Features

* User registration and login
* BCrypt password hashing
* JWT-based authentication
* Role-based authorization (`USER` / `ADMIN`)
* Event CRUD operations
* Ticket booking and cancellation
* Ticket availability management
* Pagination and sorting
* Request validation
* Global exception handling
* PostgreSQL database
* Swagger / OpenAPI documentation
* JUnit and Mockito testing
* Docker containerization
* Cloud deployment

## 🛠️ Tech Stack

| Technology        | Purpose                        |
| ----------------- | ------------------------------ |
| Java 17           | Programming language           |
| Spring Boot       | Backend framework              |
| Spring Security   | Authentication & authorization |
| JWT               | Stateless authentication       |
| Spring Data JPA   | Database access                |
| PostgreSQL        | Relational database            |
| Maven             | Build & dependency management  |
| JUnit             | Testing                        |
| Mockito           | Unit testing                   |
| Swagger / OpenAPI | API documentation              |
| Docker            | Containerization               |
| Neon              | Cloud PostgreSQL               |
| Vercel            | Cloud deployment               |

## 🏗️ Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

### Main components

```text
src/main/java/com/afifa
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── security
└── service
```

## 🔐 Authentication & Authorization

The application uses **Spring Security + JWT**.

### Authentication flow

```text
User Login
    ↓
AuthenticationManager
    ↓
UserDetailsService
    ↓
BCrypt Password Verification
    ↓
JWT Token Generated
    ↓
Client Sends Bearer Token
    ↓
JWT Authentication Filter
    ↓
SecurityContext
```

Passwords are never stored as plain text. They are hashed using BCrypt before being stored in the database.

JWT tokens contain the authenticated user's information and are validated by a custom JWT authentication filter.

## 👥 Roles

### USER

Users can:

* View events
* Create bookings
* Update/cancel their bookings

### ADMIN

Admins can:

* Create events
* Update events
* Delete events
* View events

## 📌 API Endpoints

### Authentication

| Method | Endpoint      | Description                        |
| ------ | ------------- | ---------------------------------- |
| POST   | `/auth/login` | Authenticate user and generate JWT |

### Users

| Method | Endpoint      | Description     |
| ------ | ------------- | --------------- |
| POST   | `/users`      | Register a user |
| GET    | `/users`      | Get users       |
| GET    | `/users/{id}` | Get user by ID  |
| PUT    | `/users/{id}` | Update user     |
| DELETE | `/users/{id}` | Delete user     |

### Events

| Method | Endpoint       | Access       |
| ------ | -------------- | ------------ |
| GET    | `/events`      | USER / ADMIN |
| GET    | `/events/{id}` | USER / ADMIN |
| POST   | `/events`      | ADMIN        |
| PUT    | `/events/{id}` | ADMIN        |
| DELETE | `/events/{id}` | ADMIN        |

### Bookings

Booking endpoints allow authenticated users to create, update, and cancel event bookings while maintaining ticket availability.

## 📄 API Documentation

The API is documented using **Swagger / OpenAPI**.

After starting the application locally, open:

```text
http://localhost:8080/swagger-ui/index.html
```

## ⚙️ Running Locally

### Prerequisites

* Java 17+
* Maven
* PostgreSQL
* Git

### 1. Clone the repository

```bash
git clone <your-github-repository-url>
cd event-booking-system
```

### 2. Configure environment variables

Create the required environment variables for:

```text
POSTGRES_HOST
POSTGRES_DATABASE
POSTGRES_USER
POSTGRES_PASSWORD
JWT_SECRET
```

Do not commit secrets or database credentials to GitHub.

### 3. Build the project

```bash
mvn clean package
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The application will run on:

```text
http://localhost:8080
```

## 🐳 Docker

The application can also be built and run as a Docker container.

```bash
docker build -t event-booking-system .
```

Run:

```bash
docker run -p 8080:8080 event-booking-system
```

Environment variables should be supplied separately rather than hardcoded into the image.

## ☁️ Deployment

The backend is containerized using Docker and deployed to the cloud.

The production application uses:

* Vercel for deployment
* Neon PostgreSQL for the production database
* Environment variables for database credentials and JWT secrets

The production configuration keeps sensitive credentials outside the source code.

## 🧪 Testing

Unit tests are written using:

* JUnit
* Mockito

Service-layer functionality is tested independently from the database and other external dependencies.

## 🔒 Security

Security practices implemented in the project include:

* BCrypt password hashing
* JWT authentication
* Role-based authorization
* Protected API endpoints
* Environment-based secret management
* Global exception handling
* No database credentials committed to source control

## 📚 What I Learned

Through this project, I worked with:

* Building REST APIs with Spring Boot
* Dependency Injection
* Spring Security
* JWT authentication
* Role-based authorization
* JPA and Hibernate
* PostgreSQL
* DTO-based API design
* Exception handling
* API validation
* Pagination and sorting
* Unit testing
* Docker
* Linux
* Cloud deployment

## 👩‍💻 Author

**Afifa Syed**

Computer Engineering / AI & Data Science

This project was built as a backend-focused application to strengthen Java, Spring Boot, database, security, and cloud deployment skills.
