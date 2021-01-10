# Spring Security
  - 스프링에서는 인증 및 권한 부여를 통해 리소스의 사용을 쉽게 컨트롤 할 수 있는 SpringSecurity Framwork를 제공한다. 

  ## 구조
  <img src="https://media.vlpt.us/images/2jaeyeol/post/ccefe6d6-7a4d-41d5-834b-ad56c5f6fceb/spring-security.png">

  - Spring Security는 Spring Dispatcher Servlet 의 앞단에 filter를 등록해 요청을 가로챈다.

# Security 용어
  - 인증(Authenticate) : 애플리케이션 작업의 주체(현재 유저가 누구인지 확인)
  - 인가(Authorize) : 현재 유저가 어떤 서비스, 페이지에 접근할 수 있는지 권한이 있는지 검사
  - 권한 : 인증된 주체가 애플리케이션의 동작을 수행할 수 있도록 허락되었는지를 결정

# SprintSecurity Filter
  1. ChannelProcessingFilter
  2. SecurityContextPersistenceFilter
  3. ConcurrentSessionFilter
  4. HeaderWriterFilter
  5. CsrfFilter
  6. LogoutFilter
  7. X509AuthenticationFilter
  8. AbstractPreAuthenticatedProcessingFilter
  9. CasAuthenticationFilter
  10. UsernamePasswordAuthenticationFilter
  11. BasicAuthenticationFilter
  12. SecurityContextHolderAwareRequestFilter
  13. JaasApiIntegrationFilter
  14. RememberMeAuthenticationFilter
  15. AnonymousAuthenticationFilter
  16. SessionManagementFilter
  17. ExceptionTranslationFilter
  18. FilterSecurityInterceptor
  19. SwitchUserFilter
  

# API 인증과 권한 리소스의 요청
  - 인증을 위해 가입(signup)과 로그인(signin)을 구현
  - 가입 시 제한된 리소스에 접근할 수 있는 ROLE_USER 권한을 부여
  - Spring Security 설정에 접근 제한이 필요한 리소스에 대해서 ROLE_USER 권한을 가져야 접근 가능하도록 세팅
  - 권한을 가진 회원이 로그인 성공 시 리소스에 접근할 수 있는 Jwt 보안 토큰을 발급
  - Jwt 보안 토큰으로 회원은 권한이 필요한 api 리소스를 요청해 사용한다.

# JWT(JSON Web Token)
  - JSON 객체로서 당사자간에 안전하게 정보를 전송할 수 있는 작고 독립적인 방법
  - JSON 객체를 암호화하여 만든 String 값으로 기본적으로 암호화 되어있어 변조하기 어려운 정보
  - 다른 토큰과 달리 토크 자체에 데이터를 갖고 있음

# build.gradle
  - SpringSecurity 및 Jwt관련 라이브러리를 추가
  ```java
  implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'io.jsonwebtoken:jjwt:0.9.1'
  ```

# JwtTokenProvider 
  - Jwt 토큰 생성 및 유효성 검증 컴포넌트 Jwt는 알고리즘과 비밀키로 토큰을 생성함
  - 이때 claim 정보에 토큰을 부가적으로 실어보낼 정보를 세팅할 수 있음
  - claim 정보에 회원을 구분할 수 있는 값을 세팅 후 토큰이 들어오면 해당 값으로 회원을 구분해 리소스를 제공
  ```java
  @RequiredArgsConstructor
  @Component
  public class JwtTokenProvider {
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }
  }
  ```

# JwtAuthenticationFilter
  - Jwt가 유효한 토큰인지 인증하기 위한 Filter
  - Security 설정 시 UsernamePasswordAuthenticationFilter 앞에 세팅됨
  ```java
  public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // Jwt Provier 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
  }
  ```

# SpringSecurity Configuration
  - 서버에 보안 설정 적용 
  - 아무나 접근 가능한 리소스는 permitAll()로 세팅 나머지 리소스는 'ROLE_USER' 권한이 필요함.
  - anyRequest().hasRole("USER") = anyRequest().authenticated()
  - 리소스 접근 제한 표현식
    - hasIpAddress(ip) : 접근자의 IP주소가 매칭 하는지 확인
    - hasRole(role) : 역할이 부여된 권한과 일치하는지 확인
    - hasAnyRole(role) :  부여된 역할 중 일치하는 항목이 있는지 확인 <br/>
    ex) access = "hasAnyRole('ROLE_USER','ROLE_ADMIN)"
    - permitAll : 모든 접근자를 항상 승인
    - denyAll : 모든 사용자의 접근을 거부
    - anonymous : 사용자가 익명 사용자인지 확인
    - authenticated : 인증된 사용자인지 확인
    - rememberMe : 사용자가 remember me를 사용해 인증했는지 확인
    ```java
    @RequiredArgsConstructor
    @Configuration
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

      private final JwtTokenProvider jwtTokenProvider;

      @Bean
      @Override
      public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
      }

      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/*/signin", "/*/signup").permitAll() // 가입 및 인증 주소는 누구나 접근가능
                .antMatchers(HttpMethod.GET, "helloworld/**").permitAll() // hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
                .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다

    }
    ```

# Custom UserDetailsService
  - 토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailService를 재정의
  
  ```java
  @RequiredArgsConstructor
  @Service
  public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    public UserDetails loadUserByUsername(String userPk) {
        return userJpaRepo.findById(Math.toIntExact(Long.valueOf(userPk))).orElseThrow(CUserNotFoundException::new);
    }
  }
  ```

# User Entity
  - getUserUid는 security에서 사용하는 회원 구분 uid이다. 
  - Json결과로 출력 하지 않을 데이터는<br/>
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 사용
  ```java
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUserUid() {
        return this.uid;
    }
    //계정이 만료가 되지 않았는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 잠기지 않았는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //계정 패스워드가 만료되지 않았는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정이 사용 가능한지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
  ```