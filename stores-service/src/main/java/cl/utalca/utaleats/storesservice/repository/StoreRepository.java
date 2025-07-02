package cl.utalca.utaleats.storesservice.repository;

import cl.utalca.utaleats.storesservice.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // Buscar tiendas por ciudad
    List<Store> findByCity(String city);

    // Buscar tiendas por categoría exacta
    List<Store> findByCategory(String category);

    // Buscar tiendas cuyo nombre contenga cierta palabra
    List<Store> findByNameContainingIgnoreCase(String keyword);

    // Buscar tiendas con rating mayor o igual a un valor dado
    List<Store> findByRatingGreaterThanEqual(double rating);

    // Buscar tiendas con rating menor a un valor dado
    List<Store> findByRatingLessThan(double rating);

    // Buscar tiendas por ciudad y categoría
    List<Store> findByCityAndCategory(String city, String category);

    // Buscar tiendas ordenadas por rating descendente
    List<Store> findAllByOrderByRatingDesc();

    // Buscar tiendas cuyo nombre empiece con un prefijo
    List<Store> findByNameStartingWith(String prefix);

    // Buscar tiendas cuyo nombre termine con un sufijo
    List<Store> findByNameEndingWith(String suffix);

    // Buscar tiendas cuyo nombre sea exactamente igual
    Store findByName(String name);

}

