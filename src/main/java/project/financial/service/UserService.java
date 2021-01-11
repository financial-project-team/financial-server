package project.financial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.financial.domain.User;
import project.financial.dto.UserDto;
import project.financial.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(UserDto.CreateUserRequest request){
        User user = User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .uid(request.getUid())
                .build();
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user){
        List<User> findUser = userRepository.findByUid(user.getUid());
        if(!findUser.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    public List<User> findUsers() { return userRepository.findAll();}

    public User findById(Long id){
        return userRepository.findById(id);

    }
}
