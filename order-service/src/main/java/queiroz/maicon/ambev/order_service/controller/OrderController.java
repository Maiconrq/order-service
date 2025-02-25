package queiroz.maicon.ambev.order_service.controller;

import queiroz.maicon.ambev.order_service.model.OrderRequest;
import queiroz.maicon.ambev.order_service.model.OrderStatus;
import queiroz.maicon.ambev.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/receive")
    public ResponseEntity<String> receiveOrder(@RequestBody OrderRequest orderRequest) {
        orderService.processOrder(orderRequest);
        return ResponseEntity.ok("Order received and processed");
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long orderId) {
        OrderStatus status = orderService.getOrderStatus(orderId);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
