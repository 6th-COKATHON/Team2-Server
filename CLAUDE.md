# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

### Build and Run
```bash
# Build the application
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Clean build artifacts
./gradlew clean
```

### Testing
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "UserServiceTest"

# Run specific test method
./gradlew test --tests "UserServiceTest.createUser_ValidData_ShouldReturnUserCreateResponse"
```

### Database Setup
- Local MySQL database required: `modic_local`
- Default credentials: `root/1324`
- Database will be created automatically if not exists
- JPA DDL mode set to `update`

## Architecture Overview

### Domain-Driven Design Structure

This Spring Boot application follows **Domain-Driven Design (DDD)** with **Layered Architecture**:

#### Core Layers
- **Domain Layer** (`src/main/java/hanium/modic/backend/domain/`): Business logic and entities
- **Web Layer** (`src/main/java/hanium/modic/backend/web/`): REST controllers and DTOs
- **Common Layer** (`src/main/java/hanium/modic/backend/common/`): Cross-cutting concerns

#### Domain Structure
Each domain follows this pattern:
```
domain/[domain-name]/
├── entity/         # JPA entities
├── service/        # Business logic
├── repository/     # Data access
├── enums/          # Domain-specific enums
└── dto/            # Internal DTOs (optional)
```

#### Main Domains
- **User**: User management, authentication, profiles
- **Post**: Content creation and management
- **PostLike**: Like functionality with concurrency handling
- **PostReview**: Review system for posts
- **AI**: AI image generation with RabbitMQ integration
- **Auth**: Authentication and authorization
- **Follow**: User following system
- **Image**: S3 image handling

### Key Technologies
- **Spring Boot 3.4.4** with Java 17
- **JPA/Hibernate** for data persistence
- **Spring Security** with JWT and OAuth2 (Google, Kakao)
- **MySQL** database
- **Redis** for caching and session management
- **RabbitMQ** for asynchronous processing
- **AWS S3** for image storage
- **Swagger/OpenAPI** for API documentation
- **Redisson** for distributed locking

### Common Patterns

#### Entity Design
- All entities extend `BaseEntity` for audit fields
- Use `@Builder` pattern for construction
- Entities contain business logic and validation
- No setters on entities (immutable after creation)

#### Service Layer
- Business logic resides in services
- Use `@Transactional` for write operations
- Default to `@Transactional(readOnly = true)`
- Services call repositories, not controllers calling repositories directly

#### Controller Layer
- Controllers handle HTTP concerns only
- Use `@Valid` for request validation
- Return `ResponseEntity<AppResponse<T>>` for consistency

#### Error Handling
- Custom exceptions extend `AppException`
- Use `ErrorCode` enum for consistent error responses
- Global exception handler in `GlobalExceptionHandler`

#### Testing Strategy
- Unit tests for services with Mockito
- Integration tests for controllers with `@SpringBootTest`
- Repository tests with `@DataJpaTest`
- Test data factories in `entityfactory` packages

### Security Configuration
- JWT-based authentication
- OAuth2 integration (Google, Kakao)
- CORS configuration for frontend integration
- Public endpoints defined in `application.yml`

### Performance Optimizations
- Async processing with `@Async` for non-critical operations
- Distributed locking with Redisson for concurrency
- Connection pooling and JPA optimizations
- Caching strategies with Redis

### Code Quality Standards
- Use Lombok for boilerplate reduction
- Java 17 Records for DTOs
- Constructor injection with `@RequiredArgsConstructor`
- Comprehensive validation with Bean Validation
- Korean error messages for user-facing responses

### Development Notes
- Application runs on port 8080 by default
- Swagger UI available at `/swagger-ui.html`
- Test profile uses different configuration
- Database cleanup utilities available in test base classes

### Reference Documentation
- @claude-rules/api-design.mdc
- @claude-rules/clean-code.mdc
- @claude-rules/code-conventions.mdc
- @claude-rules/project-architecture.mdc
- @claude-rules/security-patterns.mdc
- @claude-rules/testing-standards.mdc   