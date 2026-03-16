# E-Commerce Backend API (Spring Boot)

## Overview
This project is a backend REST API for an E-Commerce platform built using Spring Boot. The system provides APIs for managing users, products, carts, orders, and reviews.

The application follows a layered architecture to maintain clean code structure and scalability.

Architecture used in the project:

Controller → Service → Repository → Database

This design helps separate business logic from API logic and database operations.

## Tech Stack

### Backend Framework
Java  
Spring Boot  

### Security
Spring Security  
JWT (JSON Web Token) Authentication  

### Database
MySQL  
Spring Data JPA / Hibernate  

### Build Tool
Maven  

### Testing
JUnit  

## Features

### Authentication & Authorization
- User registration
- User login
- JWT based authentication
- Secure API endpoints

### Product Management
- Create new products
- View product list
- Update product details
- Product categorization

### Cart System
- Add product to cart
- Update cart item quantity
- Remove items from cart
- View cart details

### Order Management
- Create orders from cart
- View order history
- Manage order details

### Reviews & Ratings
- Add product review
- Add product rating
- View product feedback

## Project Structure

src/main/java/com/example/ecommerce

config - Application configuration and JWT security setup.  
controller - Handles HTTP requests and API endpoints.  
service - Contains business logic and service implementations.  
repository - Interfaces responsible for database operations using Spring Data JPA.  
model - Entity classes representing database tables.  
request - DTO classes used for incoming API request payloads.  
response - DTO classes used for structured API responses.  
exception - Custom exception handling classes.  

## Example API Endpoints

### Authentication
POST /auth/register  
POST /auth/login  

### Products
GET /products  
POST /admin/products  
PUT /admin/products/{id}  

### Cart
GET /cart  
POST /cart/add  
DELETE /cart/remove  

### Orders
POST /orders  
GET /orders  

### Reviews
POST /reviews  
POST /ratings  

## Database Configuration

The application uses MySQL for persistent storage.

Example configuration in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce  
spring.datasource.username=your_username  
spring.datasource.password=your_password  

## How to Run the Project

1. Clone the repository

git clone https://github.com/your-username/ecommerce-backend.git

2. Navigate to the project directory

cd ecommerce-backend

3. Configure the database in  
src/main/resources/application.properties

4. Run the application

./mvnw spring-boot:run

Or run the EcommerceApplication.java file directly from IntelliJ.

## Future Improvements
- Payment gateway integration
- Admin dashboard
- Inventory management
- API documentation using Swagger
- Frontend integration (React / Angular)

## Author
Anurag Sharma  
B.Tech Computer Science Engineering  

## License
This project is created for learning and demonstration purposes.
