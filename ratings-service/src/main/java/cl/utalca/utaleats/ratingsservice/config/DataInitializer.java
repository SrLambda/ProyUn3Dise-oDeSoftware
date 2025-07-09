package cl.utalca.utaleats.ratingsservice.config;

import cl.utalca.utaleats.ratingsservice.model.Rating;
import cl.utalca.utaleats.ratingsservice.repository.RatingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RatingRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Rating r1 = new Rating(1L, 1L, 1001L, 5, "¡Excelente servicio!");
                Rating r2 = new Rating(1L, 2L, 1002L, 4, "Muy buena comida, volveré.");
                Rating r3 = new Rating(2L, 11L, 2001L, 3, "Sushi fresco pero entrega lenta.");

                repository.save(r1);
                repository.save(r2);
                repository.save(r3);

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
