package parkjuida.exercise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import parkjuida.exercise.domain.Member;
import parkjuida.exercise.domain.Order;
import parkjuida.exercise.domain.item.Item;
import parkjuida.exercise.repository.OrderSearch;
import parkjuida.exercise.service.ItemService;
import parkjuida.exercise.service.MemberService;
import parkjuida.exercise.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "orders/createOrderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "orders/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
