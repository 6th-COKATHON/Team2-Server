# Spring Boot Template

[한국어](README.md) | **English**

## 📋 Project Overview

This template provides a structured starting point for developing Spring Boot-based web applications. It's designed with pre-configured core features necessary for modern web development, allowing developers to focus on business logic.

### 🛠️ Tech Stack

- **Java 21** - Latest LTS version
- **Spring Boot 3.5.3** - Latest Spring Boot framework
- **Spring Data JPA** - Data access layer
- **MySQL** - Relational database (with H2 for development)
- **Spring Security** - Security and authentication
- **JWT (JSON Web Token)** - Token-based authentication
- **Swagger/OpenAPI 3** - API documentation
- **Lombok** - Reduce boilerplate code
- **Gradle** - Build and dependency management

### ✨ Key Features

- 🏗️ **Layered Architecture** - Clear package structure and separation of concerns
- 🔐 **Built-in Security** - Pre-configured Spring Security with JWT authentication
- 📝 **Standardized Responses** - Consistent API response format
- 🔧 **Type-safe Configuration** - Configuration management through Properties classes
- 📚 **Automated API Documentation** - Real-time API docs via Swagger
- 🎯 **Claude Code Integration** - AI-powered development tools and automation hooks
- 📊 **JPA Auditing** - Automatic creation/modification time management

---

## 📁 Detailed Project Structure

```
src/main/java/com/example/demo/
├── DemoApplication.java                 # Main application class
├── common/                              # Common components
│   ├── base/                           # Base entities and configurations
│   │   ├── BaseEntity.java             # Common JPA entity fields (creation/modification time)
│   │   └── JpaAuditingConfig.java      # JPA Auditing activation configuration
│   ├── cors/                           # CORS configuration
│   │   └── CorsConfig.java             # Cross-Origin request permission settings
│   ├── error/                          # Error handling
│   │   ├── ErrorCode.java              # Standardized error code enumeration
│   │   └── exception/                  # Exception handling
│   │       ├── AppException.java       # Custom application exception
│   │       └── handler/
│   │           └── GlobalExceptionHandler.java  # Global exception handler
│   ├── property/                       # Configuration management
│   │   ├── CorsProperties.java         # CORS configuration values
│   │   ├── SwaggerProperties.java      # Swagger configuration values
│   │   └── config/
│   │       └── PropertyConfig.java     # Properties class registration
│   ├── response/                       # Standard responses
│   │   ├── AppResponse.java            # Success response wrapper
│   │   ├── BaseResponse.java           # Base response class
│   │   ├── ErrorResponse.java          # Error response wrapper
│   │   └── PageResponse.java           # Pagination response wrapper
│   ├── security/                       # Security configuration
│   │   ├── EncoderConfig.java          # Password encoder configuration
│   │   ├── SecurityConfig.java         # Spring Security main configuration
│   │   └── filter/
│   │       └── SecurityExceptionFilter.java  # Security exception filter
│   └── swagger/                        # API documentation
│       └── config/
│           └── SwaggerConfig.java      # Swagger/OpenAPI configuration
├── domain/                             # Domain logic (business entities, services)
└── web/                               # Web layer (controllers, DTOs)

.claude/                               # Claude Code configuration
├── hooks/                             # Automation hook scripts
│   ├── pre_tool_use.py               # Block dangerous commands and logging
│   ├── post_tool_use.py              # Post-tool usage logging
│   ├── notification.py               # TTS notification system
│   ├── stop.py                       # Session termination notification
│   └── utils/                        # Utilities
│       ├── llm/                      # LLM integration
│       └── tts/                      # Text-to-Speech
├── commands/                         # Custom commands
│   ├── prime.md                      # Load project context
│   ├── commit.md                     # Generate Conventional Commits
│   └── git_status.md                 # Check Git status
└── settings.local.json               # Local permission settings
```

### 📦 Package Responsibilities

#### `common` Package
Contains common components used throughout the project.

- **`base/`**: Base classes for all JPA entities and JPA Auditing configuration
- **`cors/`**: CORS configuration for handling Cross-Origin requests
- **`error/`**: Error code definitions and exception handling logic
- **`property/`**: Properties classes for type-safe configuration management
- **`response/`**: Standard response wrapper classes for API consistency
- **`security/`**: Spring Security and JWT authentication configuration
- **`swagger/`**: Swagger configuration for automatic API documentation

#### `domain` Package
Contains domain models and services that are the core of business logic.
- Entity classes
- Domain services
- Repository interfaces

#### `web` Package
Contains web layer components.
- REST controllers
- DTO (Data Transfer Object)
- Request/response models

---

## 🚀 Getting Started

### 1. Environment Setup

#### Required Environment Variables
```bash
# Database configuration
export DB_NAME=your_database_name
export DB_USERNAME=your_db_username  
export DB_PASSWORD=your_db_password

# Application configuration (optional)
export APP_NAME=your_app_name           # Default: backend
export SWAGGER_URL=localhost:8080       # Default: localhost:8080
```

#### Local Development Environment File (.env)
```bash
# Create .env file (included in gitignore)
DB_NAME=demo_db
DB_USERNAME=root
DB_PASSWORD=password
APP_NAME=my-spring-app
SWAGGER_URL=localhost:8080
```

### 2. Database Setup

#### MySQL Installation and Configuration
```bash
# Install MySQL 8.0 (macOS)
brew install mysql@8.0

# Start MySQL service
brew services start mysql@8.0

# Create database
mysql -u root -p
CREATE DATABASE your_database_name;
```

#### H2 Database (Development)
During development, you can use H2 in-memory database without additional configuration.

### 3. Running the Application

```bash
# Run with Gradle
./gradlew bootRun

# Or run DemoApplication.java in IDE
```

Once the application starts successfully:
- **API Server**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **API Docs**: http://localhost:8080/v3/api-docs

---

## ⚙️ Detailed Configuration Guide

### application.yml Structure

```yaml
spring:
  application:
    name: ${APP_NAME:backend}          # Application name
    
  datasource:                          # Datasource configuration
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/${DB_NAME}?useSSL=false&characterEncoding=UTF-8&createDatabaseIfNotExist=true&rewriteBatchedStatements=true
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    
  jpa:                                 # JPA configuration
    hibernate:
      ddl-auto: update                 # Automatic schema update
    properties:
      hibernate:
        format_sql: true               # SQL formatting
    show-sql: true                     # SQL query output
    open-in-view: false               # Disable OSIV (recommended)

cors:                                  # CORS configuration (custom)
  allowed-origins:
    - http://localhost:3000           # React development server
    - http://localhost:8080           # Same port allow

swagger:                              # Swagger configuration (custom)
  url: ${SWAGGER_URL:localhost:8080}
```

### Using Properties Classes

#### 1. CorsProperties Usage Example
```java
@RestController
public class CorsTestController {
    
    private final CorsProperties corsProperties;
    
    public CorsTestController(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }
    
    @GetMapping("/cors-config")
    public AppResponse<String[]> getCorsConfig() {
        return AppResponse.ok(corsProperties.getAllowedOrigins());
    }
}
```

#### 2. Adding New Properties Class
```java
// 1. Create Properties class
@Getter
@Setter
@ConfigurationProperties(prefix = "myapp")
public class MyAppProperties {
    private String title;
    private String version;
    private boolean debugMode;
}

// 2. Register in PropertyConfig
@Configuration
@EnableConfigurationProperties(value = {
    CorsProperties.class,
    SwaggerProperties.class,
    MyAppProperties.class  // Add new
})
public class PropertyConfig {
}

// 3. Add configuration to application.yml
myapp:
  title: "My Spring Boot App"
  version: "1.0.0"
  debug-mode: true
```

---

## 🤖 Claude Code Hook System

This project includes an advanced hook system for integration with Claude Code.

### Hook Script Descriptions

#### 1. `pre_tool_use.py` - Pre-execution Safety Check
```python
# Key features:
# - Automatically block dangerous rm -rf commands
# - Protect .env file access (sensitive information protection)
# - Log all tool usage (logs/pre_tool_use.json)
```

**Protected Command Patterns:**
- All `rm -rf` series commands
- `.env` file access (except `.env.sample`)
- Dangerous path deletion attempts

#### 2. `post_tool_use.py` - Post-execution Logging
```python
# Key features:
# - Log tool usage results (logs/post_tool_use.json)
# - Collect performance metrics
# - Preserve context on error occurrence
```

#### 3. `notification.py` - Notification System
```python
# Key features:
# - TTS (Text-to-Speech) notifications
# - Multiple TTS engine support (ElevenLabs > OpenAI > pyttsx3)
# - Engineer name personalization (ENGINEER_NAME environment variable)
```

**TTS Configuration:**
```bash
# Set environment variables
export ELEVENLABS_API_KEY=your_elevenlabs_key    # Highest priority
export OPENAI_API_KEY=your_openai_key            # Second priority
export ENGINEER_NAME="John Developer"             # Personalization (optional)
```

#### 4. `stop.py` - Session Termination Handler
```python
# Key features:
# - Session completion notification
# - Chat log cleanup (--chat option)
# - Work completion TTS guidance
```

### Custom Commands

#### `/prime` - Load Project Context
Analyzes project structure and reads README to provide project information to Claude.

```bash
/prime
```

#### `/commit` - Generate Conventional Commits
Automatically generates conventional commit messages with Korean support.

```bash
/commit              # Include default validation
/commit --no-verify  # Skip pre-validation
```

**Generated Commit Types:**
- `feat`: Add new feature
- `fix`: Bug fix  
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Refactoring
- `perf`: Performance improvement
- `test`: Add/modify tests
- `chore`: Build/tool changes

#### `/git_status` - Check Git Status
Comprehensively analyzes current git repository status.

```bash
/git_status
```

### Claude Code Configuration File

#### `settings.local.json`
```json
{
  "permissions": {
    "allow": [
      "mcp__sequential-thinking__sequentialthinking",  // Sequential thinking mode
      "Bash(./gradlew test:*)"                         // Allow Gradle tests
    ],
    "deny": []
  }
}
```

---

## 💻 Development Guide

### 1. Adding New Domain Entity

#### Step 1: Create Entity Class
```java
// src/main/java/com/example/demo/domain/user/User.java
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {  // Inherit BaseEntity for automatic creation/modification time management
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    // Constructor
    private User(String email, String password, String name, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    
    // Static factory method
    public static User create(String email, String password, String name, UserRole role) {
        return new User(email, password, name, role);
    }
}
```

#### Step 2: Create Repository Interface
```java
// src/main/java/com/example/demo/domain/user/UserRepository.java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

#### Step 3: Create Service Class
```java
// src/main/java/com/example/demo/domain/user/UserService.java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public User createUser(String email, String rawPassword, String name) {
        // Check email duplication
        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.USER_INPUT_EXCEPTION, "Email already exists");
        }
        
        // Encode password
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Create and save user
        User user = User.create(email, encodedPassword, name, UserRole.USER);
        return userRepository.save(user);
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new AppException(ErrorCode.USER_INPUT_EXCEPTION, "User not found"));
    }
}
```

### 2. Writing API Controllers

#### Step 1: Create DTO Classes
```java
// src/main/java/com/example/demo/web/user/dto/CreateUserRequest.java
@Getter
@NoArgsConstructor
public class CreateUserRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;
}

// src/main/java/com/example/demo/web/user/dto/UserResponse.java
@Getter
@RequiredArgsConstructor
public class UserResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createdAt;
    
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole(),
            user.getCreatedAt()
        );
    }
}
```

#### Step 2: Create Controller Class
```java
// src/main/java/com/example/demo/web/user/UserController.java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User Management API")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "Create User", description = "Create a new user.")
    public AppResponse<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request.getEmail(), request.getPassword(), request.getName());
        UserResponse response = UserResponse.from(user);
        return AppResponse.created(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get User", description = "Get user by ID.")
    public AppResponse<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse response = UserResponse.from(user);
        return AppResponse.ok(response);
    }
}
```

### 3. Error Handling Patterns

#### Step 1: Add Custom Error Codes
```java
// Add to ErrorCode.java
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Existing codes...
    
    // User-related errors
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "User not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U-002", "Email already exists"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U-003", "Invalid password"),
    ;
}
```

#### Step 2: Throw Exceptions in Service
```java
@Service
@RequiredArgsConstructor
public class UserService {
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
    
    @Transactional
    public User createUser(String email, String password, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        // ... creation logic
    }
}
```

#### Step 3: Automatic Exception Handling
`GlobalExceptionHandler` automatically handles exceptions and generates responses like:

```json
{
  "success": false,
  "status": 404,
  "error": {
    "code": "U-001",
    "message": "User not found"
  }
}
```

### 4. Using Standard Response Format

#### Success Responses
```java
// 200 OK
return AppResponse.ok(data);

// 201 Created  
return AppResponse.created(data);

// 204 No Content
return AppResponse.noContent();

// Custom status code
return AppResponse.of(HttpStatus.ACCEPTED, data);
```

#### Pagination Response
```java
@GetMapping
public AppResponse<PageResponse<UserResponse>> getUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPage = userService.getUsers(pageable);
    
    PageResponse<UserResponse> response = PageResponse.of(
        userPage.map(UserResponse::from)
    );
    
    return AppResponse.ok(response);
}
```

---

## 🔐 Security Configuration

### Spring Security Setup

Currently, SecurityConfig is configured to allow all requests for development convenience:

```java
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)              // Disable CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource))  // CORS configuration
            .formLogin(AbstractHttpConfigurer::disable)         // Disable form login
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll())                      // Allow all requests (development)
            .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session
            .build();
    }
}
```

### JWT Authentication Implementation Guide

#### Step 1: Create JWT Service Class
```java
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;
    
    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;
    
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateAccessToken(String userEmail, UserRole role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidity);
        
        return Jwts.builder()
            .setSubject(userEmail)
            .claim("role", role.name())
            .claim("type", "ACCESS")
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
```

#### Step 2: Create JWT Authentication Filter
```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        
        String token = resolveToken(request);
        
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.getClaimsFromToken(token);
            String userEmail = claims.getSubject();
            
            User user = userService.getUserByEmail(userEmail);
            
            UserPrincipal userPrincipal = UserPrincipal.from(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities()
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

#### Step 3: Update SecurityConfig
```java
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final SecurityExceptionFilter securityExceptionFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()           // Allow authentication API
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Allow Swagger
                .anyRequest().authenticated())                         // Require authentication for rest
            .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
```

### CORS Configuration

CORS configuration is managed through `CorsProperties` and `CorsConfig`:

```java
@Configuration
@RequiredArgsConstructor
public class CorsConfig {
    
    private final CorsProperties corsProperties;
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Use cors.allowed-origins from application.yml
        configuration.setAllowedOriginPatterns(Arrays.asList(corsProperties.getAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
```

---

## 📚 API Documentation

### Swagger/OpenAPI 3 Configuration

Swagger is automatically configured and accessible at:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI Spec**: http://localhost:8080/v3/api-docs

### Using API Documentation Annotations

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User Management API")
@RequiredArgsConstructor
public class UserController {
    
    @PostMapping
    @Operation(
        summary = "Create User",
        description = "Create a new user. Email must be unique."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public AppResponse<UserResponse> createUser(
        @Valid @RequestBody 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User creation request data",
            required = true
        ) CreateUserRequest request) {
        
        User user = userService.createUser(request.getEmail(), request.getPassword(), request.getName());
        return AppResponse.created(UserResponse.from(user));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get User", description = "Get specific user by ID.")
    public AppResponse<UserResponse> getUser(
        @Parameter(description = "User ID", required = true, example = "1")
        @PathVariable Long id) {
        
        User user = userService.getUserById(id);
        return AppResponse.ok(UserResponse.from(user));
    }
}
```

### DTO Documentation

```java
@Schema(description = "User creation request")
@Getter
@NoArgsConstructor
public class CreateUserRequest {
    
    @Schema(description = "Email address", example = "user@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Schema(description = "Password (minimum 8 characters)", example = "password123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    @Schema(description = "User name", example = "John Doe", required = true)
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;
}
```

---

## 🧪 Testing and Build

### Gradle Commands

```bash
# Build project
./gradlew build

# Run tests
./gradlew test

# Run application
./gradlew bootRun

# Create JAR file
./gradlew bootJar

# Check dependencies
./gradlew dependencies

# Clean project
./gradlew clean
```

### Test Writing Examples

#### Unit Test
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("User creation success")
    void createUser_Success() {
        // given
        String email = "test@example.com";
        String rawPassword = "password123";
        String name = "Test User";
        String encodedPassword = "encoded_password";
        
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(
            User.create(email, encodedPassword, name, UserRole.USER)
        );
        
        // when
        User result = userService.createUser(email, rawPassword, name);
        
        // then
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getName()).isEqualTo(name);
        verify(userRepository).existsByEmail(email);
        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("Exception thrown when email exists")
    void createUser_EmailExists_ThrowsException() {
        // given
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);
        
        // when & then
        assertThatThrownBy(() -> userService.createUser(email, "password", "name"))
            .isInstanceOf(AppException.class)
            .hasMessage("Email already exists");
    }
}
```

#### Integration Test
```java
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.yml")
class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("User creation API integration test")
    void createUser_Integration_Success() {
        // given
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("integration@test.com");
        request.setPassword("password123");
        request.setName("Integration Test");
        
        // when
        ResponseEntity<AppResponse> response = restTemplate.postForEntity(
            "/api/users", request, AppResponse.class
        );
        
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userRepository.existsByEmail("integration@test.com")).isTrue();
    }
}
```

---

## 🔧 Extension Guide

### 1. Adding New Properties Class

#### Step 1: Create Properties Class
```java
@Getter
@Setter
@ConfigurationProperties(prefix = "app.notification")
public class NotificationProperties {
    private boolean enabled = true;
    private String defaultTemplate;
    private Email email = new Email();
    private Sms sms = new Sms();
    
    @Getter
    @Setter
    public static class Email {
        private String host;
        private int port = 587;
        private String username;
        private String password;
        private boolean ssl = true;
    }
    
    @Getter
    @Setter
    public static class Sms {
        private String apiKey;
        private String from;
    }
}
```

#### Step 2: Register in PropertyConfig
```java
@Configuration
@EnableConfigurationProperties(value = {
    CorsProperties.class,
    SwaggerProperties.class,
    NotificationProperties.class  // Add
})
public class PropertyConfig {
}
```

#### Step 3: Add Configuration to application.yml
```yaml
app:
  notification:
    enabled: true
    default-template: "default-notification"
    email:
      host: smtp.gmail.com
      port: 587
      username: ${EMAIL_USERNAME}
      password: ${EMAIL_PASSWORD}
      ssl: true
    sms:
      api-key: ${SMS_API_KEY}
      from: "+821012345678"
```

### 2. Extending Custom Error Codes

#### Adding Domain-specific Error Codes
```java
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common Errors
    USER_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "C-001", "User input error"),
    // ... existing codes
    
    // User Domain Errors (U-XXX)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "User not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U-002", "Email already exists"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U-003", "Invalid password"),
    USER_ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "U-004", "User account is locked"),
    
    // Order Domain Errors (O-XXX)
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O-001", "Order not found"),
    INSUFFICIENT_INVENTORY(HttpStatus.BAD_REQUEST, "O-002", "Insufficient inventory"),
    ORDER_ALREADY_CANCELLED(HttpStatus.BAD_REQUEST, "O-003", "Order already cancelled"),
    
    // Payment Domain Errors (P-XXX)
    PAYMENT_FAILED(HttpStatus.BAD_REQUEST, "P-001", "Payment processing failed"),
    INVALID_CARD_INFO(HttpStatus.BAD_REQUEST, "P-002", "Invalid card information"),
    PAYMENT_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "P-003", "Payment request timeout"),
    ;
}
```

### 3. Adding Security Filter

#### Request Logging Filter
```java
@Component
@Slf4j
public class RequestLoggingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String clientIp = getClientIp(httpRequest);
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Set request ID in MDC (for log tracing)
            MDC.put("requestId", requestId);
            
            log.info("REQ [{}] {} {} from {}", requestId, method, uri, clientIp);
            
            chain.doFilter(request, response);
            
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            int status = httpResponse.getStatus();
            
            log.info("RES [{}] {} {}ms", requestId, status, duration);
            
            MDC.clear();
        }
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
```

#### Register Filter in SecurityConfig
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        // ... existing configuration
        .addFilterBefore(requestLoggingFilter, SecurityExceptionFilter.class)
        .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

### 4. Environment-specific Database Configuration

#### Environment-specific Profile Configuration
```yaml
# application.yml (common configuration)
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:local}
  application:
    name: ${APP_NAME:backend}

---
# Local environment
spring:
  config:
    activate:
      on-profile: local
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:testdb
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true

---
# Development environment
spring:
  config:
    activate:
      on-profile: dev
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

---
# Production environment
spring:
  config:
    activate:
      on-profile: prod
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
```

---

## 📞 Contact and Support

### Development Environment Troubleshooting

#### Common Issues

1. **Port Conflict (Port 8080 already in use)**
   ```bash
   # Check process using port
   lsof -i :8080
   
   # Kill process
   kill -9 <PID>
   
   # Or use different port
   ./gradlew bootRun --args='--server.port=8081'
   ```

2. **Database Connection Error**
   ```bash
   # Check MySQL service status
   brew services list | grep mysql
   
   # Start MySQL
   brew services start mysql@8.0
   
   # Check environment variables
   echo $DB_NAME
   echo $DB_USERNAME
   ```

3. **Claude Code Hook Permission Error**
   ```bash
   # Grant execution permission to hook scripts
   chmod +x .claude/hooks/*.py
   
   # Check UV installation
   which uv || curl -LsSf https://astral.sh/uv/install.sh | sh
   ```

### Additional Resources

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)
- [JWT Implementation Guide](https://github.com/jwtk/jjwt)
- [Claude Code Documentation](https://docs.anthropic.com/en/docs/claude-code)

---

## 📄 License

This project is provided under the MIT License. You are free to use, modify, and distribute it.

---

**Happy Coding! 🚀**

We hope this template helps with your Spring Boot project development. Please register an issue if you have any problems or suggestions for improvement.