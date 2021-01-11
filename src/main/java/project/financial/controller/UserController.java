package project.financial.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.User;
import project.financial.dto.UserDto;
import project.financial.repository.UserRepository;
import project.financial.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

//    @PostMapping(value = "/user")
//    public UserDto.CreateUserResponse saveUser(@RequestBody @Valid UserDto.CreateUserRequest request){
//
//    }
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
