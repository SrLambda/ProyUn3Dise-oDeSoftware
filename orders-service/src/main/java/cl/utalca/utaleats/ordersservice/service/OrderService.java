package cl.utalca.utaleats.ordersservice.service;

import cl.utalca.utaleats.ordersservice.client.StoreClient;
import cl.utalca.utaleats.ordersservice.dto.OrderDTO;
import cl.utalca.utaleats.ordersservice.dto.OrderItemDTO;
import cl.utalca.utaleats.ordersservice.exception.ResourceNotFoundException;
import cl.utalca.utaleats.ordersservice.model.Order;
import cl.utalca.utaleats.ordersservice.model.OrderItem;
import cl.utalca.utaleats.ordersservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreClient storeClient;

    public OrderService(OrderRepository orderRepository, StoreClient storeClient) {
        this.orderRepository = orderRepository;
        this.storeClient = storeClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + id + " no encontrado"));
    }

    public Order createOrder(OrderDTO orderDTO) {
        if (!storeClient.storeExists(orderDTO.getStoreId())) {
            throw new ResourceNotFoundException("La tienda con ID " + orderDTO.getStoreId() + " no existe");
        }

        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setStoreId(orderDTO.getStoreId());
        order.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : LocalDateTime.now());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus("PENDIENTE");

        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            order.addItem(item);
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, OrderDTO updatedOrderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + id + " no encontrado"));

        if (!storeClient.storeExists(updatedOrderDTO.getStoreId())) {
            throw new ResourceNotFoundException("La tienda con ID " + updatedOrderDTO.getStoreId() + " no existe");
        }

        order.setCustomerName(updatedOrderDTO.getCustomerName());
        order.setStoreId(updatedOrderDTO.getStoreId());
        order.setOrderDate(updatedOrderDTO.getOrderDate() != null ? updatedOrderDTO.getOrderDate() : order.getOrderDate());
        order.setTotalAmount(updatedOrderDTO.getTotalAmount());

        order.getItems().clear();
        for (OrderItemDTO itemDTO : updatedOrderDTO.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setOrder(order);
            order.getItems().add(item);
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: el pedido con ID " + id + " no existe");
        }
        orderRepository.deleteById(id);
    }
}
