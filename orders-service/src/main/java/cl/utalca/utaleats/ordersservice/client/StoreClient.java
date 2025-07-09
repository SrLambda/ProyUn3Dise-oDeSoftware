package cl.utalca.utaleats.ordersservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class StoreClient {

    private final RestTemplate restTemplate;

    public StoreClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean storeExists(Long storeId) {
        if (storeId == null || storeId <= 0) return false;

        try {
            String url = "http://localhost:8080/store/" + storeId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error al verificar tienda: " + e.getMessage());
            return false;
        }
    }
}