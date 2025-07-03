package cl.utalca.utaleats.ordersservice.service;

import cl.utalca.utaleats.ordersservice.dto.OrderDTO;
import cl.utalca.utaleats.ordersservice.model.Order;
import cl.utalca.utaleats.ordersservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // Constructor
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Obtener todos los pedidos
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Obtener un pedido por ID
    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null); // puedes lanzar excepción personalizada si prefieres
    }

    // Crear nuevo pedido
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setStoreId(orderDTO.getStoreId());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus("PENDIENTE");
        return orderRepository.save(order);
    }

    // Actualizar pedido existente
    public Order updateOrder(Long id, OrderDTO updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setCustomerName(updatedOrder.getCustomerName());
                    order.setStoreId(updatedOrder.getStoreId());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    return orderRepository.save(order);
                })
                .orElse(null);
    }

    // Eliminar pedido por ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
