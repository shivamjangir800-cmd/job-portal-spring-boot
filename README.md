# Job Portal Web Application

A full-stack Job Portal built using Java and Spring Boot that connects job seekers with recruiters. Candidates can browse job opportunities and submit applications, while recruiters can publish job listings and manage candidate applications.

**Live Demo:** https://job-portal-spring-boot-2.onrender.com

**GitHub Repository:** [job-portal-spring-boot](https://github.com/shivamjangir800-cmd/job-portal-spring-boot)

---

## Table of Contents

- Overview
- Features
- Technology Stack
- Application Architecture
- Database Design
- Security
- Installation and Setup
- Configuration
- Running Tests
- Deployment
- Future Enhancements
- Author

---

## Overview

The Job Portal provides separate workflows for candidates and recruiters.

Candidates can register, log in, explore available jobs, and apply for suitable positions. Recruiters can create job postings and review applications submitted by candidates.

The application uses Spring Boot for backend development, Spring Security for authentication and access control, and MySQL for persistent data storage.

## Features

### Candidate Features

- User registration and login
- Browse available job postings
- View job descriptions and requirements
- Apply for jobs
- View submitted applications
- Resume upload, if enabled in the deployed application

### Recruiter Features

- Recruiter authentication
- Create and manage job postings
- View applications submitted by candidates
- Review candidate resumes, if implemented
- Update application status, if implemented

### Backend Features

- Role-based access control
- Password hashing using BCrypt, if configured
- Database persistence using Spring Data JPA and Hibernate
- Relational database management with MySQL
- Server-side page rendering with Thymeleaf
- Environment-based configuration for deployment

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application framework |
| Spring MVC | Request handling and controllers |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access |
| Hibernate | Object-relational mapping |
| MySQL | Relational database |
| Thymeleaf | Server-side HTML rendering |
| HTML, CSS, Bootstrap | User interface |
| Maven | Dependency and build management |
| Docker | Application containerization |
| Render | Application hosting |
| Aiven MySQL | Hosted database |

---

## Application Architecture

The application follows a layered web architecture where applicable:

**Request Flow**

Browser → Spring Security → Spring MVC Controller → Service Layer, if implemented → Repository → MySQL Database

- **Controller:** Handles incoming web requests.
- **Service:** Contains business logic, where implemented.
- **Repository:** Performs database operations using Spring Data JPA.
- **Entity:** Represents persistent application data.
- **Thymeleaf:** Renders dynamic HTML pages.

Spring Security protects restricted routes and controls access based on the authenticated user's role.

---

## Database Design

The application uses MySQL to store application data.

Core data entities include:

- **User:** Stores user account information and roles.
- **Job:** Stores job titles, descriptions, locations, salary details, and requirements.
- **Application:** Stores candidate job applications and their associated status.

The exact table names and relationships depend on the entity mappings defined in the project.

Spring Data JPA and Hibernate manage persistence between Java entities and the relational database.

---

## Security

Security is an important part of the application.

- Authentication for registered users
- Role-based access restrictions for candidates and recruiters
- Password hashing using BCrypt, if configured
- Database credentials supplied through environment variables
- Restricted access to protected application routes

Production credentials and sensitive configuration values must never be committed to the repository.

---

## Installation and Setup

### Prerequisites

Install the following:

- Java Development Kit compatible with the project
- MySQL Server
- Git
- Maven, or use the included Maven Wrapper

### 1. Clone the Repository

```bash
git clone https://github.com/shivamjangir800-cmd/job-portal-spring-boot.git
cd job-portal-spring-boot
```

If the `pom.xml` and `Dockerfile` are located in a subdirectory, navigate to that directory before running the following commands.

### 2. Configure the Database

Create a MySQL database for local development.

Configure your database connection using the project's existing `application.properties` settings or environment variables.

Example environment variable names:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Use your own local database details. Do not publish production credentials.

### 3. Run the Application

On Windows, if the Maven Wrapper is included:

```bat
mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

### 4. Open the Application

Visit:

```text
http://localhost:8080
```

The application will be available once Spring Boot starts successfully.

---

## Running Tests

Execute the automated test suite using Maven:

```bash
mvn test
```

On Windows, with the Maven Wrapper:

```bat
mvnw.cmd test
```

The test suite should be expanded to cover authentication, role-based authorization, job management, application workflows, and database persistence.

---

## Deployment

The application is deployed using Render, with Aiven MySQL providing hosted database services.

Deployment configuration includes:

- Building and running the Spring Boot application
- Docker-based deployment, where configured
- Database connectivity through environment variables
- Persistent data storage in the hosted MySQL database

For deployment, configure the required environment variables in the hosting dashboard and keep all credentials private.

---

## Future Enhancements

Potential improvements include:

- Advanced job search and filtering
- Pagination and sorting
- Email notifications for application updates
- Duplicate-application prevention
- Automated unit and integration tests
- Improved logging and exception handling
- Recruiter analytics dashboard

---

## Author

**Shivam Jangir**

Aspiring Java Backend Developer

**Technical Interests:** Java, Spring Boot, Spring Security, MySQL, REST APIs, and Data Structures and Algorithms.

- **GitHub:** [shivamjangir800-cmd](https://github.com/shivamjangir800-cmd)
- **LinkedIn:** [Shivam Jangir](https://www.linkedin.com/in/shivam-jangir-a94661284/)

---