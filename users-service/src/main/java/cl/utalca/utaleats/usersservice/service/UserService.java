// users-service/src/main/java/cl/utalca/utaleats/usersservice/service/UserService.java
package cl.utalca.utaleats.usersservice.service;

import cl.utalca.utaleats.usersservice.dto.UserRegistrationDTO;
import cl.utalca.utaleats.usersservice.model.User;
import cl.utalca.utaleats.usersservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(UserRegistrationDTO registrationDTO) {
        // Validaciones: Username o email ya existen
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new RuntimeException("Username already taken: " + registrationDTO.getUsername());
        }
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already registered: " + registrationDTO.getEmail());
        }

        User newUser = new User();
        newUser.setUsername(registrationDTO.getUsername());
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setPassword(registrationDTO.getPassword()); // Guardar contraseña en texto plano

        return userRepository.save(newUser);
    }

    public Optional<User> authenticateUser(String usernameOrEmail, String password) {
        User user = userRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        // Comprobación de contraseña en texto plano
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user); // Autenticación exitosa
        }
        return Optional.empty(); // Autenticación fallida
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}