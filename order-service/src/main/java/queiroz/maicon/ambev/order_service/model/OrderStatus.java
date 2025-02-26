package queiroz.maicon.ambev.order_service.model;

public class OrderStatus {
    private Long orderId;
    private String status;

    // getters e setters
    public OrderStatus() {}

    public OrderStatus(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

