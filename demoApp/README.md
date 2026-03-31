# demoApp

This is a Spring Boot demo application implementing a simple user and post CRUD API with PostgreSQL persistence and JPA relationships.

## ✅ Java / Spring Boot features used (demoApp only)

- Spring Boot 4.0 application starter (`@SpringBootApplication`)
- Java 17 toolchain
- REST controller with path mapping
  - `@RestController`
  - `@RequestMapping("/user")` and `@RequestMapping("/post")`
  - `@PostMapping`, `@GetMapping`
  - `@PathVariable`, `@RequestBody`
- Validation with Jakarta Bean Validation
  - `@Valid` on request body
  - `@NotBlank`, `@Size`, `@Email` on DTO fields
- DTO object pattern
  - `UserModel` for incoming/outgoing payload
  - `PostModel` for post request/response payload
- JPA Entity pattern
  - `@Entity` and `@Id`
- JPA relationships
  - `@OneToMany` (User to Posts) with `CascadeType.ALL`
  - `@ManyToOne` (Post to User) with `@JoinColumn`
  - JSON serialization with `@JsonManagedReference` and `@JsonBackReference`
- Spring Data JPA repository
  - `UserRepository extends JpaRepository<UserEntity, Long>`
  - `PostRepository extends JpaRepository<PostEntity, Long>`
- Service layer with dependency injection
  - `@Service`
  - constructor injection for `UserRepository` and `PostRepository`
- Exception handling via custom unchecked exceptions
  - `UserAlreadyExistsException` (extends RuntimeException)
  - `UserNotFoundException` (extends RuntimeException)
- PostgreSQL database
  - `org.postgresql:postgresql`
- Spring Boot transaction-ready CRUD operations (save, findAll, deleteById)
- Application properties location: `src/main/resources/application.yaml` (default Spring behavior)

## 📦 Project modules
- `src/main/java/com/example/demoApp`: app code
  - `DemoAppApplication` - entry point
  - `controller/UserController` - User API endpoints
  - `controller/PostController` - Post API endpoints
  - `service/UserService` - User business logic
  - `service/PostService` - Post business logic
  - `repository/UserRepository` - User JPA repository
  - `repository/PostRepository` - Post JPA repository
  - `entity/UserEntity` - User persistence model
  - `entity/PostEntity` - Post persistence model
  - `dto/UserModel` - User request/response model
  - `dto/PostModel` - Post request/response model
  - `exception/...` - custom runtime exceptions

## 🛠️ API endpoints

- `POST /user`
  - Request body: JSON `UserModel` (id, name, email)
  - Validates `name` and `email`
  - Stores to PostgreSQL via JPA
  - Returns created `UserModel`

- `GET /user`
  - Returns all users (List<UserEntity>)

- `POST /user/{id}`
  - Deletes user by ID
  - Returns deletion message

- `POST /post/{id}`
  - Request body: JSON `PostModel` (title, content)
  - Creates a post for user with ID {id}
  - Stores to PostgreSQL via JPA with relationship
  - Returns created `PostModel`

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

- Tests are present under `src/test/java/com/example/demoApp`.
- Testing stack currently used:
  - JUnit 5
  - Mockito for mocking repositories and service dependencies
  - MockMvc for controller/web layer testing
  - `@SpringBootTest` for application context loading
- Current test coverage includes:
  - `UserServiceTest`
    - mocks `UserRepository`
    - verifies user creation flow
    - captures the entity passed to `save(...)`
    - checks basic delete-by-id behavior
  - `PostServiceTest`
    - mocks both `UserRepository` and `PostRepository`
    - validates post creation when the user exists
    - checks returned post data from the service layer
  - `UserControllerTest`
    - uses `@WebMvcTest(UserController.class)`
    - mocks `UserService` with `@MockitoBean`
    - exercises the `/user/{id}` endpoint with `MockMvc`
    - checks the current HTTP response behavior for the request
  - `PostControllerTest`
    - uses `@WebMvcTest(PostController.class)`
    - mocks `PostService` with `@MockitoBean`
    - sends a JSON request through `MockMvc`
    - verifies HTTP 200 and the returned JSON title
  - `DemoAppApplicationTests`
    - verifies that the Spring Boot application context loads successfully
- Test style in this project:
  - service tests focus on unit testing with mocked dependencies
  - controller tests focus on web-layer behavior without loading the full application
  - no real database is used in these tests; repository/service collaborators are mocked where needed
- Extra setup already included:
  - JaCoCo plugin is configured to generate test coverage reports after running the test task
