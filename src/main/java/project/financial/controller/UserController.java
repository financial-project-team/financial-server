package project.financial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.financial.advice.exception.CUserNotFoundException;
import project.financial.domain.User;
import project.financial.repository.UserJpaRepo;
import project.financial.service.ResponseService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // 결과를 처리할 Service

    /**
     * 회원 리스트 조회
     */
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepo.findAll());
    }

    /**
     * 회원 단건 조회
     */
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(@RequestParam String lang) {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.findByUid(id).orElseThrow(CUserNotFoundException::new));
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping(value = "/user")
    public SingleResult<User> modify(@RequestParam Long id, @RequestParam String name) {
        User user = User.builder()
                .id(id)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping(value = "/user/{id}")
    public CommonResult delete(@PathVariable Long id) {
        userJpaRepo.deleteById(id);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}
