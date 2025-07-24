# Spring Boot Template

**한국어** | [English](README_EN.md)

## 📋 프로젝트 소개

이 템플릿은 Spring Boot 기반의 웹 애플리케이션 개발을 위한 구조화된 시작점을 제공합니다. 현대적인 웹 개발에 필요한 핵심 기능들을 사전 구성하여, 개발자가 비즈니스 로직에 집중할 수 있도록 설계되었습니다.

### 🛠️ 기술 스택

- **Java 21** - 최신 LTS 버전
- **Spring Boot 3.5.3** - 최신 Spring Boot 프레임워크
- **Spring Data JPA** - 데이터 액세스 계층
- **MySQL** - 관계형 데이터베이스 (개발용 H2 포함)
- **Spring Security** - 보안 및 인증
- **JWT (JSON Web Token)** - 토큰 기반 인증
- **Swagger/OpenAPI 3** - API 문서화
- **Lombok** - 보일러플레이트 코드 감소
- **Gradle** - 빌드 및 의존성 관리

### ✨ 주요 특징

- 🏗️ **계층화된 아키텍처** - 명확한 패키지 구조와 책임 분리
- 🔐 **보안 내장** - Spring Security와 JWT 인증 기본 설정
- 📝 **표준화된 응답** - 일관된 API 응답 형식
- 🔧 **타입 안전한 설정** - Properties 클래스를 통한 설정 관리
- 📚 **자동 API 문서화** - Swagger를 통한 실시간 API 문서
- 🎯 **Claude Code 통합** - AI 기반 개발 도구 및 자동화 훅
- 📊 **JPA Auditing** - 자동 생성/수정 시간 관리

---

## 📁 프로젝트 구조 상세

```
src/main/java/com/example/demo/
├── DemoApplication.java                 # 메인 애플리케이션 클래스
├── common/                              # 공통 컴포넌트
│   ├── base/                           # 기본 엔티티 및 설정
│   │   ├── BaseEntity.java             # JPA 엔티티 공통 필드 (생성/수정 시간)
│   │   └── JpaAuditingConfig.java      # JPA Auditing 활성화 설정
│   ├── cors/                           # CORS 설정
│   │   └── CorsConfig.java             # Cross-Origin 요청 허용 설정
│   ├── error/                          # 에러 처리
│   │   ├── ErrorCode.java              # 표준화된 에러 코드 열거형
│   │   └── exception/                  # 예외 처리
│   │       ├── AppException.java       # 커스텀 애플리케이션 예외
│   │       └── handler/
│   │           └── GlobalExceptionHandler.java  # 전역 예외 처리기
│   ├── property/                       # 설정 관리
│   │   ├── CorsProperties.java         # CORS 설정값
│   │   ├── SwaggerProperties.java      # Swagger 설정값
│   │   └── config/
│   │       └── PropertyConfig.java     # Properties 클래스 등록
│   ├── response/                       # 표준 응답
│   │   ├── AppResponse.java            # 성공 응답 래퍼
│   │   ├── BaseResponse.java           # 기본 응답 클래스
│   │   ├── ErrorResponse.java          # 에러 응답 래퍼
│   │   └── PageResponse.java           # 페이징 응답 래퍼
│   ├── security/                       # 보안 설정
│   │   ├── EncoderConfig.java          # 패스워드 인코더 설정
│   │   ├── SecurityConfig.java         # Spring Security 메인 설정
│   │   └── filter/
│   │       └── SecurityExceptionFilter.java  # 보안 예외 필터
│   └── swagger/                        # API 문서화
│       └── config/
│           └── SwaggerConfig.java      # Swagger/OpenAPI 설정
├── domain/                             # 도메인 로직 (비즈니스 엔티티, 서비스)
└── web/                               # 웹 계층 (컨트롤러, DTO)

.claude/                               # Claude Code 설정
├── hooks/                             # 자동화 훅 스크립트
│   ├── pre_tool_use.py               # 위험 명령어 차단 및 로깅
│   ├── post_tool_use.py              # 도구 사용 후 로깅
│   ├── notification.py               # TTS 알림 시스템
│   ├── stop.py                       # 세션 종료 알림
│   └── utils/                        # 유틸리티
│       ├── llm/                      # LLM 통합
│       └── tts/                      # Text-to-Speech
├── commands/                         # 커스텀 명령어
│   ├── prime.md                      # 프로젝트 컨텍스트 로드
│   ├── commit.md                     # Conventional Commit 생성
│   └── git_status.md                 # Git 상태 확인
└── settings.local.json               # 로컬 권한 설정
```

### 📦 패키지별 역할

#### `common` 패키지
프로젝트 전반에 걸쳐 사용되는 공통 컴포넌트들을 포함합니다.

- **`base/`**: 모든 JPA 엔티티가 상속받을 기본 클래스와 JPA Auditing 설정
- **`cors/`**: Cross-Origin 요청 처리를 위한 CORS 설정
- **`error/`**: 에러 코드 정의 및 예외 처리 로직
- **`property/`**: 타입 안전한 설정값 관리를 위한 Properties 클래스들
- **`response/`**: API 응답의 일관성을 위한 표준 응답 래퍼 클래스들
- **`security/`**: Spring Security 및 JWT 인증 설정
- **`swagger/`**: API 문서 자동 생성을 위한 Swagger 설정

#### `domain` 패키지
비즈니스 로직의 핵심인 도메인 모델과 서비스를 배치합니다.
- 엔티티 클래스
- 도메인 서비스
- 리포지토리 인터페이스

#### `web` 패키지
웹 계층의 컴포넌트들을 배치합니다.
- REST 컨트롤러
- DTO (Data Transfer Object)
- 요청/응답 모델

---

## 🚀 시작하기

### 1. 환경 설정

#### 필수 환경 변수
```bash
# 데이터베이스 설정
export DB_NAME=your_database_name
export DB_USERNAME=your_db_username  
export DB_PASSWORD=your_db_password

# 애플리케이션 설정 (선택사항)
export APP_NAME=your_app_name           # 기본값: backend
export SWAGGER_URL=localhost:8080       # 기본값: localhost:8080
```

#### 로컬 개발용 환경 변수 파일 (.env)
```bash
# .env 파일 생성 (gitignore에 포함됨)
DB_NAME=demo_db
DB_USERNAME=root
DB_PASSWORD=password
APP_NAME=my-spring-app
SWAGGER_URL=localhost:8080
```

### 2. 데이터베이스 설정

#### MySQL 설치 및 설정
```bash
# MySQL 8.0 설치 (macOS)
brew install mysql@8.0

# MySQL 서비스 시작
brew services start mysql@8.0

# 데이터베이스 생성
mysql -u root -p
CREATE DATABASE your_database_name;
```

#### H2 Database (개발용)
개발 중에는 별도 설정 없이 H2 인메모리 데이터베이스를 사용할 수 있습니다.

### 3. 애플리케이션 실행

```bash
# Gradle을 통한 실행
./gradlew bootRun

# 또는 IDE에서 DemoApplication.java 실행
```

애플리케이션이 성공적으로 시작되면:
- **API 서버**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **API 문서**: http://localhost:8080/v3/api-docs

---

## ⚙️ 설정 파일 상세 가이드

### application.yml 구조

```yaml
spring:
  application:
    name: ${APP_NAME:backend}          # 애플리케이션 이름
    
  datasource:                          # 데이터소스 설정
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/${DB_NAME}?useSSL=false&characterEncoding=UTF-8&createDatabaseIfNotExist=true&rewriteBatchedStatements=true
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    
  jpa:                                 # JPA 설정
    hibernate:
      ddl-auto: update                 # 스키마 자동 업데이트
    properties:
      hibernate:
        format_sql: true               # SQL 포맷팅
    show-sql: true                     # SQL 쿼리 출력
    open-in-view: false               # OSIV 비활성화 (권장)

cors:                                  # CORS 설정 (커스텀)
  allowed-origins:
    - http://localhost:3000           # React 개발 서버
    - http://localhost:8080           # 같은 포트 허용

swagger:                              # Swagger 설정 (커스텀)
  url: ${SWAGGER_URL:localhost:8080}
```

### Properties 클래스 활용

#### 1. CorsProperties 사용 예제
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

#### 2. 새로운 Properties 클래스 추가
```java
// 1. Properties 클래스 생성
@Getter
@Setter
@ConfigurationProperties(prefix = "myapp")
public class MyAppProperties {
    private String title;
    private String version;
    private boolean debugMode;
}

// 2. PropertyConfig에 등록
@Configuration
@EnableConfigurationProperties(value = {
    CorsProperties.class,
    SwaggerProperties.class,
    MyAppProperties.class  // 새로 추가
})
public class PropertyConfig {
}

// 3. application.yml에 설정 추가
myapp:
  title: "My Spring Boot App"
  version: "1.0.0"
  debug-mode: true
```

---

## 🤖 Claude Code 훅 시스템

이 프로젝트는 Claude Code와의 통합을 위한 고도화된 훅 시스템을 포함하고 있습니다.

### 훅 스크립트 설명

#### 1. `pre_tool_use.py` - 사전 안전 검사
```python
# 주요 기능:
# - 위험한 rm -rf 명령어 자동 차단
# - .env 파일 접근 보호 (민감 정보 보호)
# - 모든 도구 사용 로깅 (logs/pre_tool_use.json)
```

**보호되는 명령어 패턴:**
- `rm -rf` 계열 모든 명령어
- `.env` 파일 접근 (`.env.sample` 제외)
- 위험한 경로 삭제 시도

#### 2. `post_tool_use.py` - 사후 로깅
```python
# 주요 기능:
# - 도구 사용 결과 로깅 (logs/post_tool_use.json)
# - 성능 메트릭 수집
# - 에러 발생 시 컨텍스트 보존
```

#### 3. `notification.py` - 알림 시스템
```python
# 주요 기능:
# - TTS (Text-to-Speech) 알림
# - 다중 TTS 엔진 지원 (ElevenLabs > OpenAI > pyttsx3)
# - 엔지니어 이름 개인화 (ENGINEER_NAME 환경변수)
```

**TTS 설정:**
```bash
# 환경변수 설정
export ELEVENLABS_API_KEY=your_elevenlabs_key    # 최우선
export OPENAI_API_KEY=your_openai_key            # 두 번째
export ENGINEER_NAME="김개발"                      # 개인화 (선택사항)
```

#### 4. `stop.py` - 세션 종료 처리
```python
# 주요 기능:
# - 세션 완료 알림
# - 대화 로그 정리 (--chat 옵션)
# - 작업 완료 TTS 안내
```

### 커스텀 명령어

#### `/prime` - 프로젝트 컨텍스트 로드
프로젝트 구조를 분석하고 README를 읽어 Claude에게 프로젝트 정보를 제공합니다.

```bash
/prime
```

#### `/commit` - Conventional Commit 생성
한국어 지원 conventional commit 메시지를 자동 생성합니다.

```bash
/commit              # 기본 검증 포함
/commit --no-verify  # 사전 검증 건너뛰기
```

**생성되는 커밋 타입:**
- `feat`: 새로운 기능 추가
- `fix`: 버그 수정  
- `docs`: 문서 변경
- `style`: 코드 스타일 변경
- `refactor`: 리팩토링
- `perf`: 성능 개선
- `test`: 테스트 추가/수정
- `chore`: 빌드/도구 변경

#### `/git_status` - Git 상태 확인
현재 git 저장소 상태를 종합적으로 분석합니다.

```bash
/git_status
```

### Claude Code 설정 파일

#### `settings.local.json`
```json
{
  "permissions": {
    "allow": [
      "mcp__sequential-thinking__sequentialthinking",  // 순차적 사고 모드
      "Bash(./gradlew test:*)"                         // Gradle 테스트 허용
    ],
    "deny": []
  }
}
```

---

## 💻 개발 가이드

### 1. 새로운 도메인 엔티티 추가

#### Step 1: 엔티티 클래스 생성
```java
// src/main/java/com/example/demo/domain/user/User.java
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {  // BaseEntity 상속으로 생성/수정 시간 자동 관리
    
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
    
    // 생성자
    private User(String email, String password, String name, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    
    // 정적 팩토리 메서드
    public static User create(String email, String password, String name, UserRole role) {
        return new User(email, password, name, role);
    }
}
```

#### Step 2: 리포지토리 인터페이스 생성
```java
// src/main/java/com/example/demo/domain/user/UserRepository.java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

#### Step 3: 서비스 클래스 생성
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
        // 이메일 중복 검사
        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.USER_INPUT_EXCEPTION, "Email already exists");
        }
        
        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // 사용자 생성 및 저장
        User user = User.create(email, encodedPassword, name, UserRole.USER);
        return userRepository.save(user);
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new AppException(ErrorCode.USER_INPUT_EXCEPTION, "User not found"));
    }
}
```

### 2. API 컨트롤러 작성

#### Step 1: DTO 클래스 생성
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

#### Step 2: 컨트롤러 클래스 생성
```java
// src/main/java/com/example/demo/web/user/UserController.java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 관리 API")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
    public AppResponse<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request.getEmail(), request.getPassword(), request.getName());
        UserResponse response = UserResponse.from(user);
        return AppResponse.created(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다.")
    public AppResponse<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse response = UserResponse.from(user);
        return AppResponse.ok(response);
    }
}
```

### 3. 에러 처리 패턴

#### Step 1: 커스텀 에러 코드 추가
```java
// ErrorCode.java에 추가
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 기존 코드들...
    
    // User 관련 에러 추가
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "User not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U-002", "Email already exists"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U-003", "Invalid password"),
    ;
}
```

#### Step 2: 서비스에서 예외 발생
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
        // ... 생성 로직
    }
}
```

#### Step 3: 자동 예외 처리
`GlobalExceptionHandler`가 자동으로 처리하여 다음과 같은 응답을 생성합니다:

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

### 4. 표준 응답 형식 사용

#### 성공 응답
```java
// 200 OK
return AppResponse.ok(data);

// 201 Created  
return AppResponse.created(data);

// 204 No Content
return AppResponse.noContent();

// 커스텀 상태 코드
return AppResponse.of(HttpStatus.ACCEPTED, data);
```

#### 페이징 응답
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

## 🔐 보안 설정

### Spring Security 구성

현재 SecurityConfig는 개발 편의성을 위해 모든 요청을 허용하도록 설정되어 있습니다:

```java
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)              // CSRF 비활성화
            .cors(cors -> cors.configurationSource(corsConfigurationSource))  // CORS 설정
            .formLogin(AbstractHttpConfigurer::disable)         // 폼 로그인 비활성화
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll())                      // 모든 요청 허용 (개발용)
            .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless 세션
            .build();
    }
}
```

### JWT 인증 구현 가이드

#### Step 1: JWT 서비스 클래스 생성
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

#### Step 2: JWT 인증 필터 생성
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

#### Step 3: SecurityConfig 업데이트
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
                .requestMatchers("/api/auth/**").permitAll()           // 인증 API 허용
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Swagger 허용
                .anyRequest().authenticated())                         // 나머지는 인증 필요
            .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
```

### CORS 설정

CORS 설정은 `CorsProperties`와 `CorsConfig`를 통해 관리됩니다:

```java
@Configuration
@RequiredArgsConstructor
public class CorsConfig {
    
    private final CorsProperties corsProperties;
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // application.yml의 cors.allowed-origins 사용
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

## 📚 API 문서화

### Swagger/OpenAPI 3 설정

Swagger는 자동으로 설정되며, 다음 URL에서 접근 가능합니다:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI 스펙**: http://localhost:8080/v3/api-docs

### API 문서화 어노테이션 사용

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "사용자 관리 API")
@RequiredArgsConstructor
public class UserController {
    
    @PostMapping
    @Operation(
        summary = "사용자 생성",
        description = "새로운 사용자를 생성합니다. 이메일은 고유해야 합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "사용자 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "409", description = "이메일 중복")
    })
    public AppResponse<UserResponse> createUser(
        @Valid @RequestBody 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "사용자 생성 요청 데이터",
            required = true
        ) CreateUserRequest request) {
        
        User user = userService.createUser(request.getEmail(), request.getPassword(), request.getName());
        return AppResponse.created(UserResponse.from(user));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "사용자 조회", description = "ID로 특정 사용자를 조회합니다.")
    public AppResponse<UserResponse> getUser(
        @Parameter(description = "사용자 ID", required = true, example = "1")
        @PathVariable Long id) {
        
        User user = userService.getUserById(id);
        return AppResponse.ok(UserResponse.from(user));
    }
}
```

### DTO 문서화

```java
@Schema(description = "사용자 생성 요청")
@Getter
@NoArgsConstructor
public class CreateUserRequest {
    
    @Schema(description = "이메일 주소", example = "user@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Schema(description = "비밀번호 (최소 8자)", example = "password123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    @Schema(description = "사용자 이름", example = "홍길동", required = true)
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;
}
```

---

## 🧪 테스트 및 빌드

### Gradle 명령어

```bash
# 프로젝트 빌드
./gradlew build

# 테스트 실행
./gradlew test

# 애플리케이션 실행
./gradlew bootRun

# JAR 파일 생성
./gradlew bootJar

# 의존성 확인
./gradlew dependencies

# 프로젝트 정리
./gradlew clean
```

### 테스트 작성 예제

#### 단위 테스트
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
    @DisplayName("사용자 생성 성공")
    void createUser_Success() {
        // given
        String email = "test@example.com";
        String rawPassword = "password123";
        String name = "테스트";
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
    @DisplayName("이메일 중복 시 예외 발생")
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

#### 통합 테스트
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
    @DisplayName("사용자 생성 API 통합 테스트")
    void createUser_Integration_Success() {
        // given
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("integration@test.com");
        request.setPassword("password123");
        request.setName("통합테스트");
        
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

## 🔧 확장 가이드

### 1. 새로운 Properties 클래스 추가

#### Step 1: Properties 클래스 생성
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

#### Step 2: PropertyConfig에 등록
```java
@Configuration
@EnableConfigurationProperties(value = {
    CorsProperties.class,
    SwaggerProperties.class,
    NotificationProperties.class  // 추가
})
public class PropertyConfig {
}
```

#### Step 3: application.yml에 설정 추가
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

### 2. 커스텀 에러 코드 확장

#### 도메인별 에러 코드 추가
```java
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common Errors
    USER_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "C-001", "User input error"),
    // ... 기존 코드들
    
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

### 3. 보안 필터 추가

#### 요청 로깅 필터
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
            // MDC에 요청 ID 설정 (로그 추적용)
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

#### SecurityConfig에 필터 등록
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        // ... 기존 설정
        .addFilterBefore(requestLoggingFilter, SecurityExceptionFilter.class)
        .addFilterBefore(securityExceptionFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

### 4. 데이터베이스 환경별 설정

#### 환경별 Profile 설정
```yaml
# application.yml (공통 설정)
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:local}
  application:
    name: ${APP_NAME:backend}

---
# Local 환경
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
# Development 환경
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
# Production 환경
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

## 📞 문의 및 지원

### 개발 환경 문제 해결

#### 일반적인 문제들

1. **포트 충돌 (Port 8080 already in use)**
   ```bash
   # 포트 사용 프로세스 확인
   lsof -i :8080
   
   # 프로세스 종료
   kill -9 <PID>
   
   # 또는 다른 포트 사용
   ./gradlew bootRun --args='--server.port=8081'
   ```

2. **데이터베이스 연결 오류**
   ```bash
   # MySQL 서비스 상태 확인
   brew services list | grep mysql
   
   # MySQL 시작
   brew services start mysql@8.0
   
   # 환경변수 확인
   echo $DB_NAME
   echo $DB_USERNAME
   ```

3. **Claude Code 훅 권한 오류**
   ```bash
   # 훅 스크립트 실행 권한 부여
   chmod +x .claude/hooks/*.py
   
   # UV 설치 확인
   which uv || curl -LsSf https://astral.sh/uv/install.sh | sh
   ```

### 추가 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Spring Security 가이드](https://spring.io/guides/gs/securing-web/)
- [JWT 구현 가이드](https://github.com/jwtk/jjwt)
- [Claude Code 문서](https://docs.anthropic.com/en/docs/claude-code)

---

## 📄 라이센스

이 프로젝트는 MIT 라이센스 하에 제공됩니다. 자유롭게 사용, 수정, 배포할 수 있습니다.

---

**Happy Coding! 🚀**

이 템플릿이 여러분의 Spring Boot 프로젝트 개발에 도움이 되기를 바랍니다. 문제가 있거나 개선사항이 있다면 언제든지 이슈를 등록해 주세요.