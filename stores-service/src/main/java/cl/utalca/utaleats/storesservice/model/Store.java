package cl.utalca.utaleats.storesservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @NotBlank(message = "La ciudad es obligatoria")
    private String city;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imageUrl;

    @DecimalMin(value = "0.0", inclusive = true, message = "El rating no puede ser menor a 0")
    @DecimalMax(value = "5.0", inclusive = true, message = "El rating no puede ser mayor a 5")
    private double rating;

    public Store() {}

    public Store(Long id, String name, String category, String city, String imageUrl, double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.city = city;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCity() {
        return city;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() { return products; }

    public void setProducts(List<Product> products) { this.products = products; }
}

