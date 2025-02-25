package queiroz.maicon.ambev.order_service.model;

public class Product {
    private Long productId;
    private Double price;

    public Product(Long productId, Double price) {
        this.productId = productId;
        this.price = price;
    }

    // getters e setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}