package cl.utalca.utaleats.ordersservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String customerName;

    @NotNull(message = "El ID de la tienda es obligatorio")
    private Long storeId;

    @PositiveOrZero(message = "El monto total no puede ser negativo")
    private double totalAmount;

    private LocalDateTime orderDate;

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    @Valid
    private List<@Valid OrderItemDTO> items;

    public OrderDTO() {}

    public OrderDTO(Long storeId, String customerName, LocalDateTime orderDate, double totalAmount, List<OrderItemDTO> items) {
        this.storeId = storeId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    // Getters y setters

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
