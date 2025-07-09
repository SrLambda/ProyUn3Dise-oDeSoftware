package cl.utalca.utaleats.storesservice.service;

import cl.utalca.utaleats.storesservice.exception.ResourceNotFoundException;
import cl.utalca.utaleats.storesservice.model.Store;
import cl.utalca.utaleats.storesservice.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService implements IStoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getStoresByCity(String city) {
        return storeRepository.findByCity(city);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tienda con ID " + id + " no encontrada"));
    }

    @Override
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Long id, Store updatedStore) {
        return storeRepository.findById(id)
                .map(store -> {
                    store.setName(updatedStore.getName());
                    store.setCategory(updatedStore.getCategory());
                    store.setCity(updatedStore.getCity());
                    store.setImageUrl(updatedStore.getImageUrl());
                    store.setRating(updatedStore.getRating());
                    return storeRepository.save(store);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Tienda con ID " + id + " no encontrada"));
    }

    @Override
    public void deleteStore(Long id) {
        if (!storeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tienda con ID " + id + " no encontrada");
        }
        storeRepository.deleteById(id);
    }
}
