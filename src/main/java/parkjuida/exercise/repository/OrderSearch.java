package parkjuida.exercise.repository;

import lombok.Getter;
import lombok.Setter;
import parkjuida.exercise.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

}
