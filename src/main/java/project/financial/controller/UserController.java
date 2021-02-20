package project.financial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.User;
import project.financial.dto.UserDto;
import project.financial.security.JwtTokenProvider;
import project.financial.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    //유저조회
    @GetMapping(value = "/{user_id}")
    public UserDto.CreateUserResponse getUserInfo(@PathVariable("user_id") Long id){
        User user = userService.findById(id);
        return new UserDto.CreateUserResponse(user);
    }

    @GetMapping
    public UserDto.Result getUsers(){
        List<User> Users = userService.findUsers();
        List<UserDto.CreateUserResponse> collect = Users.stream()
                .map(u -> new UserDto.CreateUserResponse(u))
                .collect(Collectors.toList());
        return new UserDto.Result(collect);
    }

    @DeleteMapping
    public ResponseEntity<?> withDrawal(@RequestHeader("authToken") String token){
        userService.withDrawal(jwtTokenProvider.getUserId(token));
        return ResponseEntity.ok().build();
    }
}
