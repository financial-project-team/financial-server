package project.financial.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.financial.domain.User;

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

//    public Optional<User> findByEmail(String uid) {
//        List<User> users = em.createQuery("select u from User u where u.uid =:uid", User.class)
//                .setParameter("uid", uid)
//                .getResultList();
//        return users.;
//    }
    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email =:email", User.class)
                .setParameter("email", email)
                .getResultList();
    }
    public User findById(Long id){ return em.find(User.class,id);}

    public List<User> findAll(){
        return em.createQuery("select u from User u",User.class)
                .getResultList();
    }
}
