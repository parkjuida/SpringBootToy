package parkjuida.exercise.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import parkjuida.exercise.domain.Order;
import parkjuida.exercise.repository.OrderRepository;
import parkjuida.exercise.repository.OrderSearch;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        System.out.println(all);
        return all;
    }
}
