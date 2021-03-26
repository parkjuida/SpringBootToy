package parkjuida.exercise.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import parkjuida.exercise.domain.Member;
import parkjuida.exercise.domain.Order;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public void findOne(Long id) {
        em.find(Order.class, id);
    }

}
