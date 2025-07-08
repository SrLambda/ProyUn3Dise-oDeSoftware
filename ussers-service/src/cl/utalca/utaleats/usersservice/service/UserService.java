package cl.utalca.utaleats.usersservice.service;

import cl.utalca.utaleats.usersservice.dto.UserDTO;
import cl.utalca.utaleats.usersservice.exception.ResourceNotFoundException;
import cl.utalca.utaleats.usersservice.exception.DuplicateResourceException; // Tendremos que crear esta excepción
import cl.utalca.utaleats.usersservice.model.User;
import cl.utalca.utaleats.usersservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    public User createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el email: " + userDTO.getEmail());
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // En un caso real, hashear la contraseña aquí

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO updatedUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo actualizar: usuario con ID " + id + " no existe"));

        if (!user.getEmail().equals(updatedUserDTO.getEmail()) && userRepository.existsByEmail(updatedUserDTO.getEmail())) {
            throw new DuplicateResourceException("El nuevo email ya está en uso por otro usuario: " + updatedUserDTO.getEmail());
        }

        user.setName(updatedUserDTO.getName());
        user.setEmail(updatedUserDTO.getEmail());
        user.setPassword(updatedUserDTO.getPassword()); // En un caso real, hashear la contraseña si ha cambiado

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se pudo eliminar: el usuario con ID " + id + " no existe");
        }
        userRepository.deleteById(id);
    }
}