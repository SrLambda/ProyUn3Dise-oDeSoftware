package cl.utalca.utaleats.storesservice;

import cl.utalca.utaleats.storesservice.model.Product; // importa Product
import cl.utalca.utaleats.storesservice.model.Store;
import cl.utalca.utaleats.storesservice.repository.ProductRepository; // importa ProductRepository
import cl.utalca.utaleats.storesservice.repository.StoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(StoreRepository repository, ProductRepository productRepository) {
        return args -> {
            // Solo cargar datos si no hay tiendas (para evitar duplicados en reinicios)!!!!!!!!!!!!!

            if (repository.count() == 0) {
                System.out.println("Cargando datos iniciales...");

                // Tienda 1: Pizzería La Napolitana
                Store store1 = new Store(null, "Pizzería Loca", "Pizza", "Curicó", "pizza.png", 4.5);
                store1 = repository.save(store1); // Guarda para obtener el ID

                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Pizza Margarita", "https://i.imgur.com/margarita.jpg", 8.50, store1),
                        new Product(null, "Pizza Pepperoni", "https://i.imgur.com/pepperoni.jpg", 10.00, store1),
                        new Product(null, "Pizza Cuatro Quesos", "https://i.imgur.com/cuatro_quesos.jpg", 11.50, store1),
                        new Product(null, "Calzone de Jamón", "https://i.imgur.com/calzone.jpg", 9.00, store1),
                        new Product(null, "Tiramisú", "https://i.imgur.com/tiramisu.jpg", 4.00, store1)
                ));

                // Tienda 2: Sushi Fusion
                Store store2 = new Store(null, "Sushi Go", "Sushi", "Curicó", "sushi.png", 4.2);
                store2 = repository.save(store2);

                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Nigiri Salmón (6u)", "https://i.imgur.com/nigiri_salmon.jpg", 7.50, store2),
                        new Product(null, "Roll California (8u)", "https://i.imgur.com/roll_california.jpg", 9.00, store2),
                        new Product(null, "Sashimi Mixto", "https://i.imgur.com/sashimi_mixto.jpg", 12.00, store2),
                        new Product(null, "Gyoza de Cerdo (5u)", "https://i.imgur.com/gyoza.jpg", 5.00, store2),
                        new Product(null, "Tempura Mixta", "https://i.imgur.com/tempura.jpg", 8.00, store2)
                ));

                // Tienda 3: Hamburguesas del Chef
                Store store3 = new Store(null, "Burger House", "Hamburguesas", "Talca", "burger.png", 4.8);
                store3 = repository.save(store3);

                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Burger Clásica", "https://i.imgur.com/burger_clasica.jpg", 9.50, store3),
                        new Product(null, "Burger BBQ Ahumada", "https://i.imgur.com/burger_bbq.jpg", 11.00, store3),
                        new Product(null, "Papas Fritas Grandes", "https://i.imgur.com/papas_fritas.jpg", 3.00, store3),
                        new Product(null, "Aros de Cebolla (6u)", "https://i.imgur.com/aros_cebolla.jpg", 4.50, store3),
                        new Product(null, "Milkshake Vainilla", "https://i.imgur.com/milkshake.jpg", 5.50, store3)
                ));

                // Tienda 4: El Rincón Mexicano
                Store store4 = new Store(null, "El Rincón Mexicano", "Mexicana", "Talca", "https://i.imgur.com/mexican_corner.jpg", 4.3);
                store4 = repository.save(store4);

                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Tacos al Pastor (3u)", "https://i.imgur.com/tacos_pastor.jpg", 10.50, store4),
                        new Product(null, "Burrito de Pollo", "https://i.imgur.com/burrito_pollo.jpg", 9.00, store4),
                        new Product(null, "Guacamole con Nachos", "https://i.imgur.com/guacamole.jpg", 6.00, store4),
                        new Product(null, "Enchiladas Suizas", "https://i.imgur.com/enchiladas.jpg", 11.00, store4),
                        new Product(null, "Margarita Clásica", "https://i.imgur.com/margarita_drink.jpg", 7.00, store4)
                ));

                // Tienda 5: Delicias Veganas
                Store store5 = new Store(null, "Delicias Veganas", "Vegana", "Curicó", "https://i.imgur.com/vegan_delight.jpg", 4.6);
                store5 = repository.save(store5);

                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Bowl de Quinoa y Verduras", "https://i.imgur.com/quinoa_bowl.jpg", 12.00, store5),
                        new Product(null, "Hamburguesa de Lentejas", "https://i.imgur.com/lentil_burger.jpg", 10.50, store5),
                        new Product(null, "Wrap de Hummus y Falafel", "https://i.imgur.com/falafel_wrap.jpg", 9.00, store5),
                        new Product(null, "Sopa de Lentejas Roja", "https://i.imgur.com/red_lentil_soup.jpg", 6.00, store5),
                        new Product(null, "Brownie Vegano", "https://i.imgur.com/vegan_brownie.jpg", 4.50, store5)
                ));
                System.out.println("Datos iniciales cargados.");
            } else {
                System.out.println("La base de datos ya contiene tiendas. No se cargarán datos iniciales.");
            }
        };
    }
}
