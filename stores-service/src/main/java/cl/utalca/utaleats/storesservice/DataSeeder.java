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
    CommandLineRunner initDatabase(StoreRepository storeRepository, ProductRepository productRepository) {
        return (args) -> {
            if (storeRepository.count() == 0) {
                System.out.println("Cargando datos iniciales...");

                // --- Tienda 1: Pizza ---
                Store store1 = storeRepository.save(new Store(null, "Pizzería Loca", "Pizza", "Curicó", "images/store1/store1.png", 4.5));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Pizza Margarita", "https://i.imgur.com/margarita.jpg", 8.50, store1),
                        new Product(null, "Pizza Pepperoni", "https://i.imgur.com/pepperoni.jpg", 10.00, store1),
                        new Product(null, "Pizza Cuatro Quesos", "https://i.imgur.com/cuatro_quesos.jpg", 11.50, store1),
                        new Product(null, "Calzone de Jamón", "https://i.imgur.com/calzone.jpg", 9.00, store1),
                        new Product(null, "Tiramisú", "https://i.imgur.com/tiramisu.jpg", 4.00, store1)
                ));

                // --- Tienda 2: Sushi ---
                Store store2 = storeRepository.save(new Store(null, "Sushi Go", "Sushi", "Curicó", "images/store2/store2.png", 4.2));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Nigiri Salmón (6u)", "https://i.imgur.com/nigiri_salmon.jpg", 7.50, store2),
                        new Product(null, "Roll California (8u)", "https://i.imgur.com/roll_california.jpg", 9.00, store2),
                        new Product(null, "Sashimi Mixto", "https://i.imgur.com/sashimi_mixto.jpg", 12.00, store2),
                        new Product(null, "Gyoza de Cerdo (5u)", "https://i.imgur.com/gyoza.jpg", 5.00, store2),
                        new Product(null, "Tempura Mixta", "https://i.imgur.com/tempura.jpg", 8.00, store2)
                ));

                // --- Tienda 3: Burger ---
                Store store3 = storeRepository.save(new Store(null, "Burger House", "Hamburguesas", "Talca", "images/store3/store3.jpg", 4.8));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Burger Clásica", "https://i.imgur.com/burger_clasica.jpg", 9.50, store3),
                        new Product(null, "Burger BBQ Ahumada", "https://i.imgur.com/burger_bbq.jpg", 11.00, store3),
                        new Product(null, "Papas Fritas Grandes", "https://i.imgur.com/papas_fritas.jpg", 3.00, store3),
                        new Product(null, "Aros de Cebolla (6u)", "https://i.imgur.com/aros_cebolla.jpg", 4.50, store3),
                        new Product(null, "Milkshake Vainilla", "https://i.imgur.com/milkshake.jpg", 5.50, store3)
                ));

                // --- Tienda 4: Mexicana ---
                Store store4 = storeRepository.save(new Store(null, "El Rincón Mexicano", "Mexicana", "Talca", "images/store4/store4.jpg", 4.3));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Tacos al Pastor (3u)", "https://i.imgur.com/tacos_pastor.jpg", 10.50, store4),
                        new Product(null, "Burrito de Pollo", "https://i.imgur.com/burrito_pollo.jpg", 9.00, store4),
                        new Product(null, "Guacamole con Nachos", "https://i.imgur.com/guacamole.jpg", 6.00, store4),
                        new Product(null, "Enchiladas Suizas", "https://i.imgur.com/enchiladas.jpg", 11.00, store4),
                        new Product(null, "Margarita Clásica", "https://i.imgur.com/margarita_drink.jpg", 7.00, store4)
                ));

                // --- Tienda 5: Vegana ---
                Store store5 = storeRepository.save(new Store(null, "Delicias Veganas", "Vegana", "Curicó", "images/store5/store5.jpg", 4.6));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Bowl de Quinoa y Verduras", "https://i.imgur.com/quinoa_bowl.jpg", 12.00, store5),
                        new Product(null, "Hamburguesa de Lentejas", "https://i.imgur.com/lentil_burger.jpg", 10.50, store5),
                        new Product(null, "Wrap de Hummus y Falafel", "https://i.imgur.com/falafel_wrap.jpg", 9.00, store5),
                        new Product(null, "Sopa de Lentejas Roja", "https://i.imgur.com/red_lentil_soup.jpg", 6.00, store5),
                        new Product(null, "Brownie Vegano", "https://i.imgur.com/vegan_brownie.jpg", 4.50, store5)
                ));

                // --- Tienda 6: Tienda saludable
                Store store6 = storeRepository.save(new Store(null, "Vida Sana", "Saludable", "Talca", "images/store6/store6.jpg", 4.7));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Ensalada de Quinoa", "https://i.imgur.com/ensalada_quinoa.jpg", 8.00, store6),
                        new Product(null, "Smoothie Verde", "https://i.imgur.com/smoothie_verde.jpg", 5.50, store6),
                        new Product(null, "Bowl Frutal", "https://i.imgur.com/bowl_frutal.jpg", 6.00, store6)
                ));

                // --- Tienda 7: Tienda de postres
                Store store7 = storeRepository.save(new Store(null, "Dulce Tentación", "Postres", "Curicó", "images/store7/store7.jpg", 4.9));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Cheesecake Frutilla", "https://i.imgur.com/cheesecake.jpg", 4.50, store7),
                        new Product(null, "Brownie con Helado", "https://i.imgur.com/brownie_helado.jpg", 5.00, store7),
                        new Product(null, "Mousse de Chocolate", "https://i.imgur.com/mousse_chocolate.jpg", 4.00, store7)
                ));

                // --- Tienda 8: Panadería Delicias
                Store store8 = storeRepository.save(new Store(null, "Panadería Delicias", "Panadería", "Talca", "images/store8/store8.jpg", 4.4));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Marraqueta", "https://i.imgur.com/marraqueta.jpg", 0.80, store8),
                        new Product(null, "Croissant de Chocolate", "https://i.imgur.com/croissant.jpg", 1.50, store8),
                        new Product(null, "Hallulla", "https://i.imgur.com/hallulla.jpg", 0.90, store8)
                ));

                // --- Tienda 9: Sabores Peruanos
                Store store9 = storeRepository.save(new Store(null, "Sabores Peruanos", "Peruana", "Curicó", "images/store9/store9.jpg", 4.7));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Ceviche Mixto", "https://i.imgur.com/ceviche.jpg", 10.50, store9),
                        new Product(null, "Lomo Saltado", "https://i.imgur.com/lomo_saltado.jpg", 11.00, store9),
                        new Product(null, "Ají de Gallina", "https://i.imgur.com/aji_gallina.jpg", 9.50, store9)
                ));

                // --- Tienda 10: Heladería Polar
                Store store10 = storeRepository.save(new Store(null, "Heladería Polar", "Heladería", "Talca", "images/store10/store10.jpg", 4.6));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Helado Vainilla", "https://i.imgur.com/helado_vainilla.jpg", 3.00, store10),
                        new Product(null, "Helado Chocolate", "https://i.imgur.com/helado_chocolate.jpg", 3.00, store10),
                        new Product(null, "Banana Split", "https://i.imgur.com/banana_split.jpg", 4.50, store10)
                ));

                // --- Tienda 11: Sandwichería El Paso
                Store store11 = storeRepository.save(new Store(null, "Sandwichería El Paso", "Sándwiches", "Curicó", "images/store11/store11.jpg", 4.3));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Barros Luco", "https://i.imgur.com/barrosluco.jpg", 5.00, store11),
                        new Product(null, "Ave Palta", "https://i.imgur.com/avepalta.jpg", 4.80, store11),
                        new Product(null, "Chacarero", "https://i.imgur.com/chacarero.jpg", 5.20, store11)
                ));

                // --- Tienda 12: Trattoria Italiana
                Store store12 = storeRepository.save(new Store(null, "Trattoria Italiana", "Italiana", "Talca", "images/store12/store12.jpg", 4.9));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Fettuccine Alfredo", "https://i.imgur.com/fettuccine.jpg", 9.50, store12),
                        new Product(null, "Lasaña de Carne", "https://i.imgur.com/lasagna.jpg", 10.00, store12),
                        new Product(null, "Bruschetta", "https://i.imgur.com/bruschetta.jpg", 4.00, store12)
                ));

                System.out.println("✅ Datos iniciales cargados con éxito.");
            } else {
                System.out.println("⚠️  La base de datos ya contiene tiendas. No se cargarán datos iniciales.");
            }
        };
    }
}
