package project.financial.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.User;
import project.financial.dto.UserDto;
import project.financial.repository.AuthRepository;
import project.financial.repository.UserRepository;
import project.financial.security.JwtTokenProvider;
import project.financial.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthRepository authRepository;

    // 회원가입
    @PostMapping("/signup")
    public Long join(@RequestBody Map<String, String> user) {
        return authRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/signin")
    public String login(@RequestBody Map<String, String> user) {
        User member = authRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @PostMapping(value = "/user")
    public UserDto.CreateUserResponse saveUser(@RequestBody UserDto.CreateUserRequest request){
        Long UserId = userService.join(request);
        User user = userService.findById(UserId);
        return new UserDto.CreateUserResponse(user);
    }

    @GetMapping(value = "/user/{id}")
    public UserDto.CreateUserResponse getUserInfo(@PathVariable("id") Long id){
        User user = userService.findById(id);
        return new UserDto.CreateUserResponse(user);
    }

//    @GetMapping(value = "/users")
//    public Result findAllUser() {
//        List<User> findUsers = userService.findUsers();
//        List<UserDto> collect = findUsers.stream()
//                .map(u -> new UserDto(u))
//                .collect(Collectors.toList());
//        return new Result(collect.size(), collect);
//    }
}
