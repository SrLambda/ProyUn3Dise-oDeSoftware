// cl.utalca.utaleats.ratingsservice.client.UserClient.java
package cl.utalca.utaleats.ratingsservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Value; // Para leer la URL del users-service desde properties

@Component
public class UserClient {

    private final RestTemplate restTemplate;

    // Puedes obtener la URL del users-service desde application.properties
    // Esto es mejor que hardcodearla aquí.
    @Value("${users-service.url:http://localhost:8083}") // Valor por defecto si no se encuentra la propiedad
    private String usersServiceUrl;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Verifica si un usuario existe llamando al users-service.
     * @param userId El ID del usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean userExists(Long userId) {
        if (userId == null || userId <= 0) {
            return false;
        }

        try {
            String url = usersServiceUrl + "/api/users/" + userId; // Usar la URL configurada
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            System.err.println("Usuario con ID " + userId + " no encontrado en users-service.");
            return false;
        } catch (Exception e) {
            System.err.println("Error al comunicarse con users-service para verificar usuario " + userId + ": " + e.getMessage());
            return false;
        }
    }
}