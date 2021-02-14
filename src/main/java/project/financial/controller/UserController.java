package project.financial.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.Stock;
import project.financial.domain.User;
import project.financial.dto.UserDto;
import project.financial.dto.UserStockDto;
import project.financial.repository.AuthRepository;
import project.financial.repository.UserRepository;
import project.financial.security.JwtTokenProvider;
import project.financial.service.StockService;
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
@RequestMapping("/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    //유저조회
    @GetMapping(value = "/{user_id}")
    public UserDto.CreateUserResponse getUserInfo(@RequestHeader("authToken") String token ,
                                                  @PathVariable("user_id") Long id){
        User user = userService.findById(jwtTokenProvider.getUserId(token));

        return new UserDto.CreateUserResponse(user);
    }








}
