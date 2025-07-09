// cl.utalca.utaleats.ordersservice.client.UserClient.java
package cl.utalca.utaleats.ordersservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UserClient {

    private final RestTemplate restTemplate;

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
            // URL de tu users-service
            // Asegúrate que el puerto 8083 sea el correcto para tu users-service
            // Y que el endpoint sea /api/users/{id}
            String url = "http://localhost:8083/api/users/" + userId;
            restTemplate.getForObject(url, Object.class); // Intenta obtener el objeto de usuario
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            // Si users-service devuelve 404 (Not Found), el usuario no existe
            System.err.println("Usuario con ID " + userId + " no encontrado en users-service.");
            return false;
        } catch (Exception e) {
            // Captura otros errores (ej. users-service no está corriendo, problemas de red)
            System.err.println("Error al comunicarse con users-service para verificar usuario " + userId + ": " + e.getMessage());
            // Para propósitos de prueba, puedes decidir si quieres que esto devuelva false o lance una excepción.
            // Por ahora, devolver false para no detener la aplicación si el users-service no está disponible.
            return false;
        }
    }
}