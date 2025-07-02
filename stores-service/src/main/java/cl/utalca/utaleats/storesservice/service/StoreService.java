package cl.utalca.utaleats.storesservice.service;

import cl.utalca.utaleats.storesservice.model.Store;
import cl.utalca.utaleats.storesservice.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService implements IStoreService{

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getStoresByCity(String city) {
        return storeRepository.findByCity(city);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreById(Long id) {
        Optional<Store> optionalStore = storeRepository.findById(id);
        return optionalStore.orElse(null); // o lanza una excepción si prefieres
    }

    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

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
                .orElseGet(() -> {
                    updatedStore.setId(id);
                    return storeRepository.save(updatedStore);
                });
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
