# Shopify Auth Backend Service

## 📚 Table of Contents
- [What is this project?](#what-is-this-project)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Quick Start Guide](#quick-start-guide)
- [API Endpoints](#api-endpoints)
- [Understanding the Code](#understanding-the-code)
- [Troubleshooting](#troubleshooting)

---

## What is this project?

This is a **backend authentication service** for an e-commerce application (like Shopify). It's built using **Spring Boot** (a Java framework) and provides user authentication, order management, and secure API access using JWT (JSON Web Tokens).

### What does it do?
- ✅ **User Registration**: Create new user accounts
- ✅ **User Login**: Authenticate users with JWT tokens (secure login system)
- ✅ **Order Management**: Create and retrieve orders for authenticated users
- ✅ **Database Storage**: Stores all data in PostgreSQL database
- ✅ **Dockerized**: Runs in containers for easy deployment

### Why Docker?
Docker allows you to run the application in isolated containers without installing Java, PostgreSQL, or other dependencies directly on your machine. It makes deployment consistent across different environments.

---

## Technologies Used

| Technology | Purpose | Why We Use It |
|------------|---------|---------------|
| **Java 21** | Programming Language | Modern, robust, enterprise-grade |
| **Spring Boot** | Backend Framework | Simplifies building REST APIs |
| **PostgreSQL** | Database | Reliable, open-source SQL database |
| **Docker** | Containerization | Easy deployment and isolation |
| **Gradle** | Build Tool | Manages dependencies and builds the project |
| **JWT** | Authentication | Secure token-based authentication |

---

## Project Structure

```
auth-backend/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/shopify/
│   │   │   ├── ShopifyApplication.java     # Main application entry point
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java     # Security & JWT configuration
│   │   │   ├── controller/
│   │   │   │   └── UserController.java     # API endpoints (routes)
│   │   │   ├── model/
│   │   │   │   ├── User.java               # User data structure
│   │   │   │   ├── Orders.java             # Order data structure
│   │   │   │   └── OrderRequest.java       # Order request format
│   │   │   ├── repository/                 # Database access layer
│   │   │   ├── security/                   # JWT & security utilities
│   │   │   └── service/                    # Business logic
│   │   └── resources/
│   │       └── application.properties      # App configuration
│   └── test/                               # Test files
├── Dockerfile                              # Instructions to build Docker image
├── docker-compose.yml                      # Orchestrates multiple containers
├── build.gradle                            # Project dependencies
└── README.md                               # This file!
```

---

## Prerequisites

Before you begin, make sure you have the following installed:

1. **Docker Desktop** (includes Docker and Docker Compose)
   - Download from: https://www.docker.com/products/docker-desktop
   - Check installation: `docker --version` and `docker-compose --version`

2. **Git** (to clone the repository)
   - Download from: https://git-scm.com/
   - Check installation: `git --version`

3. **(Optional) Java 21** - Only needed if running without Docker
   - Download from: https://adoptium.net/

4. **(Optional) Postman or curl** - For testing API endpoints
   - Postman: https://www.postman.com/downloads/

---

## Quick Start Guide

### Option 1: Run with Docker (Recommended for Beginners)

This is the easiest way to run the application. Docker will handle everything!

```bash
# Step 1: Navigate to the project directory
cd auth-backend

# Step 2: Build the Java application
./gradlew clean build

# Step 3: Start all services (database + backend)
docker-compose up --build

# You should see logs indicating:
# ✓ PostgreSQL database is running
# ✓ Spring Boot application has started
# ✓ Server is listening on port 8080
```

**What just happened?**
- Docker created a PostgreSQL database container
- Docker built your Spring Boot application into another container
- Both containers can communicate with each other
- Your API is now accessible at `http://localhost:8080`

### Option 2: Run Locally (Without Docker)

If you want to run the application directly on your machine:

```bash
# Step 1: Start PostgreSQL manually (or use Docker for just the database)
docker run --name postgres-local -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=shopify_db -p 5432:5432 -d postgres:latest

# Step 2: Build and run the Spring Boot application
./gradlew clean build
./gradlew bootRun

# Alternative: Use the run script
chmod +x run.sh
./run.sh
```

---

## API Endpoints

Once the application is running, you can interact with these endpoints:

### 1. Create a New User (Registration)

```bash
POST http://localhost:8080/create
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

### 2. Login (Get JWT Token)

```bash
POST http://localhost:8080/jwtLogin
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

**Important:** Save this token! You'll need it for authenticated requests.

### 3. Create an Order (Requires Authentication)

```bash
POST http://localhost:8080/putOrders
Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN_HERE

{
  "userId": 1,
  "productName": "Laptop",
  "quantity": 1,
  "price": 1200.00
}
```

### 4. Get All Users

```bash
GET http://localhost:8080/getAll
```

### 5. Get Orders by User (Requires Authentication)

```bash
GET http://localhost:8080/getOrdersByUser?userId=1
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

---

## Understanding the Code

### How Authentication Works (JWT)

1. **User registers** → Password is encrypted and stored in database
2. **User logs in** → Server verifies password and generates a JWT token
3. **User makes requests** → Includes JWT token in request header
4. **Server validates token** → Grants or denies access

### Key Files Explained

#### `ShopifyApplication.java`
The main entry point. When you run the app, this is where it starts.

#### `SecurityConfig.java`
Configures which endpoints require authentication and which are public.

#### `UserController.java`
Defines all the API endpoints (routes). Each method handles a specific HTTP request.

#### `application.properties`
Configuration file containing:
- Database connection details
- Server port
- JWT secret key
- Hibernate settings

#### `docker-compose.yml`
Defines two services:
- **postgres**: PostgreSQL database container
- **shopify-app**: Your Spring Boot application container

---

## Troubleshooting

### Port Already in Use

**Error:** `Port 8080 is already in use`

**Solution:**
```bash
# Find what's using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change the port in application.properties
server.port=8081
```

### Database Connection Failed

**Error:** `Connection refused` or `Database does not exist`

**Solution:**
```bash
# Stop all containers
docker-compose down

# Remove volumes and restart
docker-compose down -v
docker-compose up --build
```

### Gradle Build Failed

**Error:** Build errors during `./gradlew build`

**Solution:**
```bash
# Clean and rebuild
./gradlew clean
./gradlew build --refresh-dependencies
```

### Docker Not Starting

**Solution:**
- Ensure Docker Desktop is running
- Check Docker has enough resources (Settings → Resources)
- Try restarting Docker Desktop

### JWT Token Expired

**Error:** `401 Unauthorized` on authenticated endpoints

**Solution:**
- Login again to get a fresh token
- Tokens expire after a set duration (check SecurityConfig)

---

## Stopping the Application

```bash
# Stop Docker containers (keeps data)
docker-compose stop

# Stop and remove containers (removes data)
docker-compose down

# Stop, remove containers, and delete database volumes
docker-compose down -v
```

---

## Next Steps

Once you're comfortable with the basics:

1. **Explore the code** - Open files in an IDE like IntelliJ IDEA or VS Code
2. **Add features** - Try adding new endpoints or fields to the User model
3. **Test thoroughly** - Write unit tests for your services
4. **Learn more** - Study Spring Boot, JWT, and PostgreSQL documentation

---

## Resources for Learning

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Docker Getting Started](https://docs.docker.com/get-started/)
- [PostgreSQL Tutorial](https://www.postgresql.org/docs/)
- [JWT Introduction](https://jwt.io/introduction)

---

## Need Help?

If you encounter issues:
1. Check the logs: `docker-compose logs -f shopify-app`
2. Verify database is running: `docker ps`
3. Test endpoints with Postman or curl
4. Review error messages carefully

Happy coding! 🚀
