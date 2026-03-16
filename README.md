E-Commerce Backend API (Spring Boot)
Overview

This project is a RESTful backend API for an E-Commerce platform built using Spring Boot. It provides secure and scalable APIs for managing users, products, carts, orders, reviews, and ratings.

The application is designed using a layered architecture to ensure clean code organization, better maintainability, and clear separation of concerns.

Architecture
Controller → Service → Repository → Database

This architecture helps separate:

API handling logic

business logic

data access logic

Tech Stack
Backend

Java

Spring Boot

Security

Spring Security

JWT Authentication

Database

MySQL

Spring Data JPA / Hibernate

Build Tool

Maven

Testing

JUnit

Features
Authentication & Authorization

User registration

User login

JWT-based authentication

Secured API endpoints

Role-based access support for admin and users

Product Management

Create new products

View all products

Update product details

Categorize products

Manage products from admin side

Cart System

Add products to cart

Update cart item quantity

Remove items from cart

View complete cart details

Order Management

Create orders from cart

View order history

Manage order details

Admin order handling

Reviews & Ratings

Add product reviews

Add product ratings

View customer feedback for products

Project Structure
src/main/java/com/example/ecommerce
│
├── config       # Application configuration and JWT security setup
├── controller   # REST API controllers
├── service      # Business logic layer
├── repository   # Database access layer using Spring Data JPA
├── model        # Entity classes mapped to database tables
├── request      # DTOs for incoming request payloads
├── response     # DTOs for outgoing API responses
└── exception    # Global and custom exception handling
Example API Endpoints
Authentication

POST /auth/register

POST /auth/login

Products

GET /products

POST /admin/products

PUT /admin/products/{id}

Cart

GET /cart

POST /cart/add

DELETE /cart/remove

Orders

POST /orders

GET /orders

Reviews & Ratings

POST /reviews

POST /ratings

Database Configuration

The application uses MySQL for persistent storage.

Example configuration in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=your_username
spring.datasource.password=your_password
How to Run the Project
1. Clone the repository
git clone https://github.com/your-username/ecommerce-backend.git
2. Navigate to the project directory
cd ecommerce-backend
3. Configure the database

Update the database credentials in:

src/main/resources/application.properties
4. Run the application
./mvnw spring-boot:run

Or run the EcommerceApplication.java file directly from your IDE.

Future Improvements

Payment gateway integration

Inventory management

API documentation with Swagger / OpenAPI

Admin dashboard for analytics and management

Frontend integration with React or Angular

Deployment using Docker and cloud platforms

Author

Anurag Sharma
B.Tech – Computer Science Engineering

License

This project is created for learning, practice, and demonstration purposes.
