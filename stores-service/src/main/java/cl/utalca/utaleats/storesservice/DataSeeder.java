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
                        new Product(null, "Pizza Margarita", "images/store1/product1.jpg", 8.50, store1),
                        new Product(null, "Pizza Pepperoni", "images/store1/product2.jpg", 10.00, store1),
                        new Product(null, "Pizza Cuatro Quesos", "images/store1/product3.jpg", 11.50, store1),
                        new Product(null, "Calzone de Jamón", "images/store1/product4.jpg", 9.00, store1),
                        new Product(null, "Tiramisú", "images/store1/product5.jpg", 4.00, store1),
                        new Product(null, "Pizza Hawaiana", "images/store1/product6.jpg", 10.50, store1),
                        new Product(null, "Pizza Vegetariana", "images/store1/product7.jpg", 9.80, store1),
                        new Product(null, "Stromboli de Pepperoni", "images/store1/product8.jpg", 10.20, store1),
                        new Product(null, "Pan de Ajo con Queso", "images/store1/product9.jpg", 3.50, store1),
                        new Product(null, "Cannoli Siciliano", "images/store1/product10.jpg", 4.20, store1)
                ));

                // --- Tienda 2: Sushi ---
                Store store2 = storeRepository.save(new Store(null, "Sushi Go", "Sushi", "Curicó", "images/store2/store2.png", 4.2));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Nigiri Salmón (6u)", "images/store2/product1.jpg", 7.50, store2),
                        new Product(null, "Roll California (8u)", "images/store2/product2.jpg", 9.00, store2),
                        new Product(null, "Sashimi Mixto", "images/store2/product3.jpg", 12.00, store2),
                        new Product(null, "Gyoza de Cerdo (5u)", "images/store2/product4.jpg", 5.00, store2),
                        new Product(null, "Tempura Mixta", "images/store2/product5.jpg", 8.00, store2),
                        new Product(null, "Uramaki Atún Picante", "images/store2/product6.jpg", 9.50, store2),
                        new Product(null, "Roll Dragón", "images/store2/product7.jpg", 11.00, store2),
                        new Product(null, "Yakimeshi de Verduras", "images/store2/product8.jpg", 6.00, store2),
                        new Product(null, "Tonkatsu Don", "images/store2/product9.jpg", 10.00, store2),
                        new Product(null, "Mochi de Té Verde", "images/store2/product10.jpg", 3.80, store2)
                ));

                // --- Tienda 3: Burger ---
                Store store3 = storeRepository.save(new Store(null, "Burger House", "Hamburguesas", "Talca", "images/store3/store3.jpg", 4.8));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Burger Clásica", "images/store3/product1.jpg", 9.50, store3),
                        new Product(null, "Burger BBQ Ahumada", "images/store3/product2.jpg", 11.00, store3),
                        new Product(null, "Papas Fritas Grandes", "images/store3/product3.jpg", 3.00, store3),
                        new Product(null, "Aros de Cebolla (6u)", "images/store3/product4.jpg", 4.50, store3),
                        new Product(null, "Milkshake Vainilla", "images/store3/product5.jpg", 5.50, store3)
                ));

                // --- Tienda 4: Mexicana ---
                Store store4 = storeRepository.save(new Store(null, "El Rincón Mexicano", "Mexicana", "Talca", "images/store4/store4.jpg", 4.3));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Tacos al Pastor (3u)", "images/store4/product1.jpg", 10.50, store4),
                        new Product(null, "Burrito de Pollo", "images/store4/product2.jpg", 9.00, store4),
                        new Product(null, "Guacamole con Nachos", "images/store4/product3.jpg", 6.00, store4),
                        new Product(null, "Enchiladas Suizas", "images/store4/product4.jpg", 11.00, store4),
                        new Product(null, "Margarita Clásica", "images/store4/product5.jpg", 7.00, store4)
                ));

                // --- Tienda 5: Vegana ---
                Store store5 = storeRepository.save(new Store(null, "Delicias Veganas", "Vegana", "Curicó", "images/store5/store5.jpg", 4.6));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Bowl de Quinoa y Verduras", "images/store5/product1.jpg", 12.00, store5),
                        new Product(null, "Hamburguesa de Lentejas", "images/store5/product2.jpg", 10.50, store5),
                        new Product(null, "Wrap de Hummus y Falafel", "images/store5/product3.jpg", 9.00, store5),
                        new Product(null, "Sopa de Lentejas Roja", "images/store5/product4.jpg", 6.00, store5),
                        new Product(null, "Brownie Vegano", "images/store5/product5.jpg", 4.50, store5)
                ));

                // --- Tienda 6: Tienda saludable
                Store store6 = storeRepository.save(new Store(null, "Vida Sana", "Saludable", "Talca", "images/store6/store6.jpg", 4.7));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Ensalada de Quinoa", "images/store6/product1.jpg", 8.00, store6),
                        new Product(null, "Smoothie Verde", "images/store6/product2.jpg", 5.50, store6),
                        new Product(null, "Bowl Frutal", "images/store6/product3.jpg", 6.00, store6)
                ));

                // --- Tienda 7: Tienda de postres
                Store store7 = storeRepository.save(new Store(null, "Dulce Tentación", "Postres", "Curicó", "images/store7/store7.jpg", 4.9));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Cheesecake Frutilla", "images/store7/product1.jpg", 4.50, store7),
                        new Product(null, "Brownie con Helado", "images/store7/product2.jpg", 5.00, store7),
                        new Product(null, "Mousse de Chocolate", "images/store7/product3.jpg", 4.00, store7)
                ));

                // --- Tienda 8: Panadería Delicias
                Store store8 = storeRepository.save(new Store(null, "Panadería Delicias", "Panadería", "Talca", "images/store8/store8.jpg", 4.4));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Marraqueta", "images/store8/product1.jpg", 0.80, store8),
                        new Product(null, "Croissant de Chocolate", "images/store8/product2.jpg", 1.50, store8),
                        new Product(null, "Hallulla", "images/store8/product3.jpg", 0.90, store8)
                ));

                // --- Tienda 9: Sabores Peruanos
                Store store9 = storeRepository.save(new Store(null, "Sabores Peruanos", "Peruana", "Curicó", "images/store9/store9.jpg", 4.7));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Ceviche Mixto", "images/store9/product1.jpg", 10.50, store9),
                        new Product(null, "Lomo Saltado", "images/store9/product2.jpg", 11.00, store9),
                        new Product(null, "Ají de Gallina", "images/store9/product3.jpg", 9.50, store9)
                ));

                // --- Tienda 10: Heladería Polar
                Store store10 = storeRepository.save(new Store(null, "Heladería Polar", "Heladería", "Talca", "images/store10/store10.jpg", 4.6));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Helado Vainilla", "images/store10/product1.jpg", 3.00, store10),
                        new Product(null, "Helado Chocolate", "images/store10/product2.jpg", 3.00, store10),
                        new Product(null, "Banana Split", "images/store10/product3.jpg", 4.50, store10)
                ));

                // --- Tienda 11: Sandwichería El Paso
                Store store11 = storeRepository.save(new Store(null, "Sandwichería El Paso", "Sándwiches", "Curicó", "images/store11/store11.jpg", 4.3));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Barros Luco", "images/store11/product1.jpg", 5.00, store11),
                        new Product(null, "Ave Palta", "images/store11/product2.jpg", 4.80, store11),
                        new Product(null, "Chacarero", "images/store11/product3.jpg", 5.20, store11)
                ));

                // --- Tienda 12: Trattoria Italiana
                Store store12 = storeRepository.save(new Store(null, "Trattoria Italiana", "Italiana", "Talca", "images/store12/store12.jpg", 4.9));
                productRepository.saveAll(Arrays.asList(
                        new Product(null, "Fettuccine Alfredo", "images/store12/product1.jpg", 9.50, store12),
                        new Product(null, "Lasaña de Carne", "images/store12/product2.jpg", 10.00, store12),
                        new Product(null, "Bruschetta", "images/store12/product3.jpg", 4.00, store12)
                ));

                System.out.println("✅ Datos iniciales cargados con éxito.");
            } else {
                System.out.println("⚠️  La base de datos ya contiene tiendas. No se cargarán datos iniciales.");
            }
        };
    }
}
