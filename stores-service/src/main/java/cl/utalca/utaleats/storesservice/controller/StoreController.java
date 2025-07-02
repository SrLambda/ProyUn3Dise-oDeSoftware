package cl.utalca.utaleats.storesservice.controller;

import cl.utalca.utaleats.storesservice.model.Store;
import cl.utalca.utaleats.storesservice.service.StoreService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/store")
@CrossOrigin(origins = "*") // Permite llamadas desde el frontend
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> getStoresByCity(@RequestParam(required = false) String city) {
        if (city != null && !city.isBlank()) {
            return storeService.getStoresByCity(city);
        } else {
            return storeService.getAllStores();
        }
    }

    // Obtener una tienda por ID
    @GetMapping("/{id}")
    public Store getStoreById(@PathVariable Long id) {
        return storeService.getStoreById(id);
    }

    // Crear una nueva tienda
    @PostMapping
    public Store createStore(@Valid @RequestBody Store store) {
        return storeService.createStore(store);
    }

    // Actualizar una tienda existente
    @PutMapping("/{id}")
    public Store updateStore(@PathVariable Long id, @Valid @RequestBody Store updatedStore) {
        return storeService.updateStore(id, updatedStore);
    }

    // Eliminar una tienda
    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
    }

}

