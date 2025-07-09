package cl.utalca.utaleats.ordersservice.controller;

import cl.utalca.utaleats.ordersservice.dto.OrderDTO;
import cl.utalca.utaleats.ordersservice.model.Order;
import cl.utalca.utaleats.ordersservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*") // Permite llamadas desde cualquier frontend
public class OrderController {

    private final OrderService orderService;

    // Constructor
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Obtener todos los pedidos
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Crear un nuevo pedido
    @PostMapping
    public Order createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    // Actualizar un pedido existente
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/user/{userId}") // Por ejemplo, /orders/user/1
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }
}
