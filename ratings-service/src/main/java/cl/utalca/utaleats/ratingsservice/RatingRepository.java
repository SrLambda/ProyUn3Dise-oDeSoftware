package cl.utalca.utaleats.ratingsservice;

import cl.utalca.utaleats.ratingsservice.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByStoreId(Long storeId);
}