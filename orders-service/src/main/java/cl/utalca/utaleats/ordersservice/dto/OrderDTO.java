package cl.utalca.utaleats.ordersservice.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class OrderDTO {

    //private Long storeId;
    //private String customerName;
    private LocalDateTime orderDate;
    //private double totalAmount;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String customerName;

    @NotNull(message = "El ID de la tienda es obligatorio")
    private Long storeId;

    @PositiveOrZero(message = "El monto total no puede ser negativo")
    private double totalAmount;

    public OrderDTO() {}

    public OrderDTO(Long storeId, String customerName, LocalDateTime orderDate, double totalAmount) {
        this.storeId = storeId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
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
}
