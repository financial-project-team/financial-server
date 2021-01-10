package project.financial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import project.financial.advice.exception.CUserNotFoundException;
import project.financial.repository.UserJpaRepo;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    public UserDetails loadUserByUsername(String userPk) {
        return userJpaRepo.findById(Math.toIntExact(Long.valueOf(userPk))).orElseThrow(CUserNotFoundException::new);
    }
}