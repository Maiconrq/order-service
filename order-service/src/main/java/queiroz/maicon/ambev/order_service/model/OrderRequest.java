package queiroz.maicon.ambev.order_service.model;

import java.util.List;

public class OrderRequest {
    private Long orderId;
    private List<Product> products;

    // getters e setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}