package parkjuida.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import parkjuida.exercise.domain.*;
import parkjuida.exercise.domain.item.Book;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initDb1();
        initService.initDb2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void initDb1() {
            Member userA = createMember("userA", "서울", "4321", "4231");

            Book book1 = createBook("Book1", 10000, 100);
            Book book2 = createBook("Book2", 20000, 100);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(userA);
            Order order = Order.createOrder(userA, delivery, orderItem1, orderItem2);
            em.persist(order);

            Member userB = new Member();
            userB.setName("userB");
        }


        public void initDb2() {
            Member userA = createMember("userB", "양양", "1234", "5672");

            Book bookA = createBook("A Book", 30000, 100);
            Book bookB = createBook("B book", 50000, 200);

            OrderItem orderItem1 = OrderItem.createOrderItem(bookA, 30000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(bookB, 50000, 6);

            Delivery delivery = createDelivery(userA);
            Order order = Order.createOrder(userA, delivery, orderItem1, orderItem2);
            em.persist(order);

            Member userB = new Member();
            userB.setName("userB");
        }

        private Delivery createDelivery(Member userA) {
            Delivery delivery = new Delivery();
            delivery.setAddress(userA.getAddress());
            return delivery;
        }

        private Book createBook(String book12, int i, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(book12);
            book1.setPrice(i);
            book1.setStockQuantity(stockQuantity);
            em.persist(book1);
            return book1;
        }

        private Member createMember(String userB2, String city, String street, String zipcode) {
            Member userA = new Member();
            userA.setName(userB2);
            userA.setAddress(new Address(city, street, zipcode));
            em.persist(userA);
            return userA;
        }
    }

}
