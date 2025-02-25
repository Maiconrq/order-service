package queiroz.maicon.ambev.order_service.service;

import queiroz.maicon.ambev.order_service.model.Order;
import queiroz.maicon.ambev.order_service.model.OrderRequest;
import queiroz.maicon.ambev.order_service.model.OrderStatus;
import queiroz.maicon.ambev.order_service.model.Product;
import queiroz.maicon.ambev.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void processOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderId(orderRequest.getOrderId());
        order.setProducts(orderRequest.getProducts());
        order.setTotalValue(calculateTotalValue(orderRequest.getProducts()));
        order.setStatus("PROCESSING");
        orderRepository.save(order);
    }

    public OrderStatus getOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            return new OrderStatus(order.getOrderId(), order.getStatus());
        }
        return null;
    }

    private Double calculateTotalValue(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
