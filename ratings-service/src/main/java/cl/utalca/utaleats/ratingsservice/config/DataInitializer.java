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
                repository.save(new Rating(1L, 101L, 1001L, 5, "¡Excelente servicio!"));
                repository.save(new Rating(1L, 102L, 1002L, 4, "Muy buena comida, volveré."));
                repository.save(new Rating(2L, 103L, 2001L, 3, "Sushi fresco pero entrega lenta."));
            }
        };
    }
}
