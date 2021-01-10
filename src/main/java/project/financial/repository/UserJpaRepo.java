package project.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.financial.domain.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUid(String email);
}
