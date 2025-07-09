package cl.utalca.utaleats.storesservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imageUrl;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser menor a 0")
    private double price;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @JsonBackReference
    private Store store;

    public Product() {}

    public Product(Long id, String name, String imageUrl, double price, Store store) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.store = store;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
}
