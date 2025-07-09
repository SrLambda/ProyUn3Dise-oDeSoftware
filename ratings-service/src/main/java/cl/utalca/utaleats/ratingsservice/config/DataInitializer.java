package cl.utalca.utaleats.ratingsservice.config;

import cl.utalca.utaleats.ratingsservice.model.Rating;
import cl.utalca.utaleats.ratingsservice.repository.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
        // Asegúrate que este esté aquí también para la inicialización
    CommandLineRunner initDatabase(RatingRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Usa IDs de usuario que sabes que existen en tu users-service (ej. 1, 2, 3, 4)
                Rating r1 = new Rating(1L, 1L, 1L, 5, "¡Excelente servicio!"); // storeId, productId, userId
                Rating r2 = new Rating(1L, 2L, 2L, 4, "Muy buena comida, volveré.");
                Rating r3 = new Rating(2L, 11L, 3L, 3, "Sushi fresco pero entrega lenta.");
                // Puedes añadir más
                Rating r4 = new Rating(1L, 1L, 4L, 5, "¡Rápido y delicioso!");

                repository.save(r1);
                repository.save(r2);
                repository.save(r3);
                repository.save(r4); // Guardar el nuevo rating

                System.out.println("⭐ Datos precargados:");
                for (Rating r : repository.findAll()) {
                    System.out.println(" → Store ID: " + r.getStoreId() +
                            ", Product ID: " + r.getProductId() +
                            ", User ID: " + r.getUserId() +
                            ", Score: " + r.getScore() +
                            ", Comment: " + r.getComment());
                }
            }
        };
    }
}
