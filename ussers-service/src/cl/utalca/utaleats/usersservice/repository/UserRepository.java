package cl.utalca.utaleats.usersservice.repository;

import cl.utalca.utaleats.usersservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar un usuario por su email
    Optional<User> findByEmail(String email);

    // Verificar si un usuario existe por su email
    boolean existsByEmail(String email);
}