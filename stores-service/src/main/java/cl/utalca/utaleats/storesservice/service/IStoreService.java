package cl.utalca.utaleats.storesservice.service;

import cl.utalca.utaleats.storesservice.model.Store;
import java.util.List;

public interface IStoreService {
    List<Store> getStoresByCity(String city);
    List<Store> getAllStores();
    Store getStoreById(Long id);
    Store createStore(Store store);
    Store updateStore(Long id, Store updatedStore);
    void deleteStore(Long id);
}
