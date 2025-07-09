// users-service/src/main/java/cl/utalca/utaleats/usersservice/repository/UserRepository.java
package cl.utalca.utaleats.usersservice.repository;

import cl.utalca.utaleats.usersservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}