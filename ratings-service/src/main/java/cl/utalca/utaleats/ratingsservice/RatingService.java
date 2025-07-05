package cl.utalca.utaleats.ratingsservice;

import cl.utalca.utaleats.ratingsservice.Rating;
import cl.utalca.utaleats.ratingsservice.RatingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    public List<Rating> getRatingsByStoreId(Long storeId) {
        return ratingRepository.findByStoreId(storeId);
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // añadir lógica para actualizar el rating promedio en StoreService
    // usando una llamada HTTP a StoreService o un Message Queue.
}