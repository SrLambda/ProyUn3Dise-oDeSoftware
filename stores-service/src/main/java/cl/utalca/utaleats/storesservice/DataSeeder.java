package cl.utalca.utaleats.storesservice;

import cl.utalca.utaleats.storesservice.model.Store;
import cl.utalca.utaleats.storesservice.repository.StoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(StoreRepository repository) {
        return args -> {
            repository.save(new Store(null, "Pizza Loca", "Pizzería", "Curicó", "pizza.png", 4.5));
            repository.save(new Store(null, "Sushi Go", "Sushi", "Curicó", "sushi.png", 4.2));
            repository.save(new Store(null, "Burger House", "Hamburguesas", "Talca", "burger.png", 4.8));
        };
    }
}
