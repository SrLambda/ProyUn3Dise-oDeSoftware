// users-service/src/main/java/cl/utalca/utaleats/usersservice/UsersServiceApplication.java
package cl.utalca.utaleats.usersservice;

import cl.utalca.utaleats.usersservice.model.User;
import cl.utalca.utaleats.usersservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
    }

    // Definir un bean CommandLineRunner para ejecutar código al inicio de la aplicación
    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Borra todos los usuarios existentes para un inicio limpio (opcional para desarrollo)
            userRepository.deleteAll();

            // Crea y guarda usuarios de prueba con contraseñas en texto plano
            userRepository.save(new User("john.doe", "john.doe@example.com", "password123"));
            userRepository.save(new User("jane.smith", "jane.smith@example.com", "securepass"));
            userRepository.save(new User("admin", "admin@utalca.cl", "adminpass"));

            System.out.println("Usuarios de prueba precargados:");
            userRepository.findAll().forEach(System.out::println);
        };
    }
}