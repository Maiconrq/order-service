package queiroz.maicon.ambev.order_service.controller;

import queiroz.maicon.ambev.order_service.model.OrderRequest;
import queiroz.maicon.ambev.order_service.model.OrderStatus;
import queiroz.maicon.ambev.order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testReceiveOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(1L);

        mockMvc.perform(post("/orders/receive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Order received and processed"));

        verify(orderService, times(1)).processOrder(any(OrderRequest.class));
    }

    @Test
    void testGetOrderStatus_Found() throws Exception {
        Long orderId = 1L;
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus("PROCESSING");

        when(orderService.getOrderStatus(orderId)).thenReturn(orderStatus);

        mockMvc.perform(get("/orders/status/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(orderStatus)));

        verify(orderService, times(1)).getOrderStatus(orderId);
    }

    @Test
    void testGetOrderStatus_NotFound() throws Exception {
        Long orderId = 1L;

        when(orderService.getOrderStatus(orderId)).thenReturn(null);

        mockMvc.perform(get("/orders/status/{orderId}", orderId))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).getOrderStatus(orderId);
    }
}
