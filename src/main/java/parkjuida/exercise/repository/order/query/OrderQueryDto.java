package parkjuida.exercise.repository.order.query;

import lombok.Data;
import parkjuida.exercise.domain.Address;
import parkjuida.exercise.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(
            Long orderId,
            String name,
            LocalDateTime orderDate,
            OrderStatus orderStatus,
            Address address
    ) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public void setOrderItems(List<OrderItemQueryDto> orderItems) {
        this.orderItems = orderItems;
    }
}
