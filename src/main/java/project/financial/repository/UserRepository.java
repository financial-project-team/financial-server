package project.financial.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.financial.domain.Stock;
import project.financial.domain.User;
import project.financial.domain.UserStock;
import project.financial.dto.UserStockDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(User user){em.persist(user);}


    public User findById(Long id){ return em.find(User.class,id);}

    public List<User> findAll(){
        return em.createQuery("select u from User u",User.class)
                .getResultList();
    }


}
