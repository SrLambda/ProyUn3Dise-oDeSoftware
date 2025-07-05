package cl.utalca.utaleats.ratingsservice;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la tienda es obligatorio")
    private Long storeId; // O productId, o ambos, según lo que se califique

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación no puede ser mayor a 5")
    private Integer score; // Calificación de 1 a 5

    private String comment;
    private String customerName; // Quien califica
    private LocalDateTime ratingDate;

    // Constructors, getters, setters
    public Rating() {
        this.ratingDate = LocalDateTime.now();
    }

    public Rating(Long storeId, Integer score, String comment, String customerName) {
        this.storeId = storeId;
        this.score = score;
        this.comment = comment;
        this.customerName = customerName;
        this.ratingDate = LocalDateTime.now();
    }

    // ... Getters and Setters ...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public LocalDateTime getRatingDate() { return ratingDate; }
    public void setRatingDate(LocalDateTime ratingDate) { this.ratingDate = ratingDate; }
}