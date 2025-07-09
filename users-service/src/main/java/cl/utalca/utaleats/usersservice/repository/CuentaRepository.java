package cl.utalca.utaleats.usersservice.repository;

import cl.utalca.utaleats.usersservice.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByCorreo(String correo);
}
