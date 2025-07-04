package cl.utalca.utaleats.ordersservice.dto;

import jakarta.validation.constraints.*;

public class OrderItemDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private int quantity;

    @PositiveOrZero(message = "El precio debe ser igual o mayor a 0")
    private double price;

    // Constructor vacío
    public OrderItemDTO() {}

    // Constructor completo
    public OrderItemDTO(Long productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters y setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
