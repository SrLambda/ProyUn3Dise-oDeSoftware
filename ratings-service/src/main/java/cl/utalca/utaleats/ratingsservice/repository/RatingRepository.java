package cl.utalca.utaleats.ratingsservice.repository;

import cl.utalca.utaleats.ratingsservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Buscar todas las calificaciones de una tienda específica
    List<Rating> findByStoreId(Long storeId);

    // Buscar todas las calificaciones hechas por un usuario específico
    List<Rating> findByUserId(Long userId);

    // Buscar calificación única por usuario y tienda (si se desea prevenir duplicados)
    Rating findByStoreIdAndUserId(Long storeId, Long userId);
}
