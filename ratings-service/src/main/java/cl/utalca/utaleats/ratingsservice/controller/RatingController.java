package cl.utalca.utaleats.ratingsservice.controller;

import cl.utalca.utaleats.ratingsservice.dto.RatingDTO;
import cl.utalca.utaleats.ratingsservice.model.Rating;
import cl.utalca.utaleats.ratingsservice.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@CrossOrigin(origins = "*") // Permitir peticiones desde el frontend
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Obtener todas las calificaciones
    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    // Obtener calificación por ID
    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }

    // Obtener calificaciones por ID de tienda
    @GetMapping("/store/{storeId}")
    public List<Rating> getRatingsByStoreId(@PathVariable Long storeId) {
        return ratingService.getRatingsByStoreId(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<Rating> getRatingsByProductId(@PathVariable Long productId) {
        return ratingService.getRatingsByProductId(productId);
    }

    // Obtener calificaciones por ID de usuario
    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsByUserId(@PathVariable Long userId) {
        return ratingService.getRatingsByUserId(userId);
    }

    // Crear nueva calificación
    @PostMapping
    public Rating createRating(@Valid @RequestBody RatingDTO ratingDTO) {
        System.out.println("📝 Recibiendo comentario:");
        System.out.println("Store ID: " + ratingDTO.getStoreId());
        System.out.println("Product ID: " + ratingDTO.getProductId());
        System.out.println("User ID: " + ratingDTO.getUserId());
        System.out.println("Score: " + ratingDTO.getScore());
        System.out.println("Comment: " + ratingDTO.getComment());
        return ratingService.createRating(ratingDTO);
    }

    // Actualizar calificación
    @PutMapping("/{id}")
    public Rating updateRating(@PathVariable Long id, @Valid @RequestBody RatingDTO ratingDTO) {
        return ratingService.updateRating(id, ratingDTO);
    }

    // Eliminar calificación
    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
    }
}
