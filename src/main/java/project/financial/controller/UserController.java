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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthRepository authRepository;

    @GetMapping(value = "/{id}")
    public UserDto.CreateUserResponse getUserInfo(@RequestHeader("authToken") String token ,
                                                  @PathVariable("id") Long id){
        User user = userService.findById(jwtTokenProvider.getUserId(token));
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
