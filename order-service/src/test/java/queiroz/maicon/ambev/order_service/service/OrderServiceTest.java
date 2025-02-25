package queiroz.maicon.ambev.order_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import queiroz.maicon.ambev.order_service.model.Order;
import queiroz.maicon.ambev.order_service.model.OrderRequest;
import queiroz.maicon.ambev.order_service.model.OrderStatus;
import queiroz.maicon.ambev.order_service.model.Product;
import queiroz.maicon.ambev.order_service.repository.OrderRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(1L);
        orderRequest.setProducts(Arrays.asList(
                new Product(1L, 10.0),
                new Product(2L, 20.0)
        ));

        Order order = new Order();
        order.setOrderId(1L);
        order.setProducts(orderRequest.getProducts());
        order.setTotalValue(30.0);
        order.setStatus("PROCESSING");

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.processOrder(orderRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrderStatus() {
        Long orderId = 1L;
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus("PROCESSING");

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(order));

        OrderStatus status = orderService.getOrderStatus(orderId);

        assertEquals(orderId, status.getOrderId());
        assertEquals("PROCESSING", status.getStatus());
    }
}