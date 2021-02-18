package project.financial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.User;
import project.financial.dto.UserDto;
import project.financial.repository.AuthRepository;
import project.financial.security.JwtTokenProvider;
import project.financial.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody Map<String, String> user) {
        User user1;
        try {
            user1 = authRepository.save(User.builder()
                    .account(0)
                    .dateTime(LocalDateTime.now())
                    .name(user.get("name"))
                    .email(user.get("email"))
                    .password(passwordEncoder.encode(user.get("password")))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
        } catch ( Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().body(new UserDto.CreateUserResponse(user1));
    }



    // 로그인
    @PostMapping("/signin")
    public String login(@RequestBody Map<String, String> user) {
        User member = authRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getId(), member.getUsername(), member.getRoles());
    }
}
