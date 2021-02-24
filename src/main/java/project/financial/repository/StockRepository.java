package project.financial.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.financial.domain.Stock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class StockRepository {

    @PersistenceContext
    private final EntityManager em;

    public Stock findByName(String name) {
        return em.createQuery("select s from Stock s where s.name =: name", Stock.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
