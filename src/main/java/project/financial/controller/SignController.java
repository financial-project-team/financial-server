package project.financial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.financial.domain.User;
import project.financial.advice.exception.CEmailSigninFailedException;
import project.financial.security.JwtTokenProvider;
import project.financial.repository.UserJpaRepo;
import project.financial.service.ResponseService;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1")
public class SignController {

    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 로그인
     * @param uid
     * @param password
     * @return
     */
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@RequestParam String uid, @RequestParam String password) {
        User user = userJpaRepo.findByUid(uid).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));

    }

    /**
     * 회원가입
     * @param uid
     * @param password
     * @param name
     * @return
     */
    @PostMapping(value = "/signup")
    public CommonResult signup(@RequestParam String uid, @RequestParam String password, @RequestParam String name) {

        userJpaRepo.save(User.builder()
                .uid(uid)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }
}