package cl.utalca.utaleats.ordersservice.repository;

import cl.utalca.utaleats.ordersservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    // Buscar pedidos por ID de tienda
    List<Order> findByStoreId(Long storeId);
}

