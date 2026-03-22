# demoApp

This is a Spring Boot demo application implementing a simple user CRUD API with in-memory H2 persistence.

## ✅ Java / Spring Boot features used (demoApp only)

- Spring Boot 4.0 application starter (`@SpringBootApplication`)
- Java 17 toolchain
- REST controller with path mapping
  - `@RestController`
  - `@RequestMapping("/user")`
  - `@PostMapping`, `@GetMapping`
  - `@PathVariable`, `@RequestBody`
- Validation with Jakarta Bean Validation
  - `@Valid` on request body
  - `@NotBlank`, `@Size`, `@Email` on DTO fields
- DTO object pattern
  - `UserModel` for incoming/outgoing payload
- JPA Entity pattern
  - `@Entity` and `@Id`
- Spring Data JPA repository
  - `UserRepository extends JpaRepository<UserEntity, Long>`
- Service layer with dependency injection
  - `@Service`
  - constructor injection for `UserRepository`
- Exception handling via custom unchecked exceptions
  - `UserAlreadyExistsException` (extends RuntimeException)
  - `UserNotFoundException` (extends RuntimeException)
- H2 in-memory database
  - `com.h2database:h2`
- Spring Boot transaction-ready CRUD operations (save, findAll, deleteById)
- Application properties location: `src/main/resources/application.properties` (default Spring behavior)

## 📦 Project modules
- `src/main/java/com/example/demoApp`: app code
  - `DemoAppApplication` - entry point
  - `controller/UserController` - API endpoints
  - `service/UserService` - business logic
  - `repository/UserRepository` - JPA repository
  - `entity/UserEntity` - persistence model
  - `dto/UserModel` - request/response model
  - `exception/...` - custom runtime exceptions

## 🛠️ API endpoints

- `POST /user`
  - Request body: JSON `UserModel` (id, name, email)
  - Validates `name` and `email`
  - Stores to H2 via JPA
  - Returns created `UserModel`

- `GET /user`
  - Returns all users (List<UserEntity>)

- `POST /user/{id}`
  - Deletes user by ID
  - Returns deletion message

## 📌 Notes
- `UserEntity.id` is currently manually set (ID generation commented out).
- `deleteById` uses `POST` for deletion, could be replaced by `@DeleteMapping("/{id}")` for RESTful semantics.
- Validation errors are automatically handled by Spring Boot (HTTP 400 with details).

## ▶️ Run locally

```bash
./gradlew bootRun
```

Open `http://localhost:8080/user` via Postman / curl.

## 🧪 Tests

- No tests found in `src/test` for demoApp yet (optional next step).
