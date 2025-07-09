// cl.utalca.utaleats.usersservice.config.DataInicializer.java (Archivo existente, modificado)
package cl.utalca.utaleats.usersservice.config;

import cl.utalca.utaleats.usersservice.service.DataInitializerService; // Nuevo import
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInicializer {

    private final DataInitializerService dataInitializerService; // Inyecta el nuevo servicio

    public DataInicializer(DataInitializerService dataInitializerService) {
        this.dataInitializerService = dataInitializerService;
    }

    @Bean
    CommandLineRunner init() { // Ya no necesitamos inyectar los repositorios aquí directamente
        return args -> {
            dataInitializerService.initializeData(); // Llama al método transaccional del servicio
        };
    }
}