// cl.utalca.utaleats.ratingsservice.service.RatingService.java
package cl.utalca.utaleats.ratingsservice.service;

import cl.utalca.utaleats.ratingsservice.client.StoreClient;
import cl.utalca.utaleats.ratingsservice.client.UserClient; // ¡Nuevo import!
import cl.utalca.utaleats.ratingsservice.dto.RatingDTO;
import cl.utalca.utaleats.ratingsservice.exception.ResourceNotFoundException;
import cl.utalca.utaleats.ratingsservice.model.Rating;
import cl.utalca.utaleats.ratingsservice.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final StoreClient storeClient;
    private final UserClient userClient; // ¡Nuevo cliente inyectado!

    // Constructor actualizado
    public RatingService(RatingRepository ratingRepository, StoreClient storeClient, UserClient userClient) {
        this.ratingRepository = ratingRepository;
        this.storeClient = storeClient;
        this.userClient = userClient; // Asignar el nuevo cliente
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación con ID " + id + " no encontrada"));
    }

    public List<Rating> getRatingsByStoreId(Long storeId) {
        return ratingRepository.findByStoreId(storeId);
    }

    public List<Rating> getRatingsByProductId(Long productId) {
        return ratingRepository.findByProductId(productId);
    }

    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }

    public Rating createRating(RatingDTO ratingDTO) {
        // Validación del ID de usuario
        if (!userClient.userExists(ratingDTO.getUserId())) { // ¡Nueva validación!
            throw new ResourceNotFoundException("El usuario con ID " + ratingDTO.getUserId() + " no existe.");
        }
        // Validar si la tienda existe
        if (!storeClient.storeExists(ratingDTO.getStoreId())) {
            throw new ResourceNotFoundException("La tienda con ID " + ratingDTO.getStoreId() + " no existe.");
        }

        Rating rating = new Rating();
        rating.setStoreId(ratingDTO.getStoreId());
        rating.setProductId(ratingDTO.getProductId());
        rating.setUserId(ratingDTO.getUserId());
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());

        return ratingRepository.save(rating);
    }

    public Rating updateRating(Long id, RatingDTO updatedDTO) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo actualizar: calificación con ID " + id + " no existe"));

        // Validación del ID de usuario
        if (!userClient.userExists(updatedDTO.getUserId())) { // ¡Nueva validación!
            throw new ResourceNotFoundException("El usuario con ID " + updatedDTO.getUserId() + " no existe.");
        }
        // Validar tienda si se está actualizando
        if (!storeClient.storeExists(updatedDTO.getStoreId())) {
            throw new ResourceNotFoundException("La tienda con ID " + updatedDTO.getStoreId() + " no existe.");
        }

        rating.setStoreId(updatedDTO.getStoreId());
        rating.setProductId(updatedDTO.getProductId());
        rating.setUserId(updatedDTO.getUserId());
        rating.setScore(updatedDTO.getScore());
        rating.setComment(updatedDTO.getComment());

        return ratingRepository.save(rating);
    }

    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se pudo eliminar: calificación con ID " + id + " no existe");
        }
        ratingRepository.deleteById(id);
    }
}