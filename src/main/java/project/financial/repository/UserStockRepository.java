package project.financial.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.financial.domain.Stock;
import project.financial.domain.User;
import project.financial.domain.UserStock;
import project.financial.dto.UserStockDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserStockRepository {
    @PersistenceContext
    private final EntityManager em;


    public void save(UserStock userStock){
        em.persist(userStock);
        em.flush();
    }

    public List<UserStock> showMyStock(Long userId){
        return em.createQuery("select u from UserStock u where u.user =:userId", UserStock.class)
                .setParameter("userId",userId)
                .getResultList();
    }
}
