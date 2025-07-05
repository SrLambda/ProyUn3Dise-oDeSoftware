package cl.utalca.utaleats.ratingsservice.client;

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
        try {
            String url = "http://localhost:8080/store/" + storeId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
