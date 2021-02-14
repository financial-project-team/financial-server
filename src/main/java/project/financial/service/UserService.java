package project.financial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.financial.domain.User;
import project.financial.repository.AuthRepository;
import project.financial.repository.UserRepository;
import project.financial.repository.UserStockRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final UserStockRepository userStockRepository;

    public List<User> findUsers() { return userRepository.findAll();}

    public User findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
