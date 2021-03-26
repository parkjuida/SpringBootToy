package parkjuida.exercise.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import parkjuida.exercise.domain.Address;
import parkjuida.exercise.domain.Member;
import parkjuida.exercise.domain.Order;
import parkjuida.exercise.domain.OrderStatus;
import parkjuida.exercise.domain.item.Book;
import parkjuida.exercise.exception.NotEnoughStockException;
import parkjuida.exercise.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void orderItem() throws Exception {
        Member member = createMember();
        Book book = createBook("Test Book", 10000, 100);

        int orderCount = 10;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        Assert.assertEquals(OrderStatus.ORDER, order.getStatus());
        Assert.assertEquals(1, order.getOrderItems().size());
        Assert.assertEquals(10000 * orderCount, order.getTotalPrice());
        Assert.assertEquals(book.getStockQuantity(), 100 - orderCount);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("seoul", "gangnam", "SW1W 0NY"));
        em.persist(member);
        return member;
    }

    @Test(expected = NotEnoughStockException.class)
    public void orderOverStockQuantity() throws Exception {
        Member member = createMember();
        Book book = createBook("Test Book", 10000, 10);

        orderService.order(member.getId(), book.getId(), 11);

        fail("should throw NotEnoughStockException");
    }

    @Test
    public void orderCancel() throws Exception {
        Member member = createMember();
        Book book = createBook("Book", 8000, 10);

        Long id = orderService.order(member.getId(), book.getId(), 10);
        orderService.cancelOrder(id);
        Order order = orderRepository.findOne(id);

        Assert.assertEquals(OrderStatus.CANCEL, order.getStatus());
        Assert.assertEquals(10, book.getStockQuantity());
    }
}
