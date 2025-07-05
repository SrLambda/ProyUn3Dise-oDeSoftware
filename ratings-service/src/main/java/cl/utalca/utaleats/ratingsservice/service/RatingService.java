package cl.utalca.utaleats.ratingsservice.service;

import cl.utalca.utaleats.ratingsservice.dto.RatingDTO;
import cl.utalca.utaleats.ratingsservice.exception.ResourceNotFoundException;
import cl.utalca.utaleats.ratingsservice.model.Rating;
import cl.utalca.utaleats.ratingsservice.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
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

    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }

    public Rating createRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setStoreId(ratingDTO.getStoreId());
        rating.setUserId(ratingDTO.getUserId());
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Long id, RatingDTO updatedDTO) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo actualizar: calificación con ID " + id + " no existe"));

        rating.setStoreId(updatedDTO.getStoreId());
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
