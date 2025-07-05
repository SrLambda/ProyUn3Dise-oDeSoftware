package cl.utalca.utaleats.ratingsservice.dto;

import jakarta.validation.constraints.*;

public class RatingDTO {

    @NotNull(message = "El ID de la tienda es obligatorio")
    private Long storeId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private int score;

    private String comment;

    // Constructores
    public RatingDTO() {}

    public RatingDTO(Long storeId, Long userId, int score, String comment) {
        this.storeId = storeId;
        this.userId = userId;
        this.score = score;
        this.comment = comment;
    }

    // Getters y setters
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
