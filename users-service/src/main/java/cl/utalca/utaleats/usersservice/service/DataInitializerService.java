// cl.utalca.utaleats.usersservice.service.DataInitializerService.java
package cl.utalca.utaleats.usersservice.service;

import cl.utalca.utaleats.usersservice.model.Cuenta;
import cl.utalca.utaleats.usersservice.model.Perfil;
import cl.utalca.utaleats.usersservice.repository.CuentaRepository;
import cl.utalca.utaleats.usersservice.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataInitializerService {

    private final CuentaRepository cuentaRepository;
    private final PerfilRepository perfilRepository;

    public DataInitializerService(CuentaRepository cuentaRepository, PerfilRepository perfilRepository) {
        this.cuentaRepository = cuentaRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public void initializeData() {
        // Solo inicializar datos si no hay cuentas existentes
        if (cuentaRepository.count() == 0) {
            // Cuentas y perfiles existentes
            Perfil perfil1 = new Perfil("Juan Pérez", 912345678, "Av. Central 123");
            perfilRepository.save(perfil1);

            Perfil perfil2 = new Perfil("Ana Díaz", 987654321, "Calle Falsa 456");
            perfilRepository.save(perfil2);

            Cuenta cuenta1 = new Cuenta("juan@example.com", "1234", perfil1);
            cuentaRepository.save(cuenta1);

            Cuenta cuenta2 = new Cuenta("ana@example.com", "abcd", perfil2);
            cuentaRepository.save(cuenta2);

            // --- ¡Nuevas Cuentas y Perfiles! ---

            // Nueva cuenta 3
            Perfil perfil3 = new Perfil("Carlos Gómez", 955554444, "Calle del Sol 789");
            perfilRepository.save(perfil3);
            Cuenta cuenta3 = new Cuenta("carlos@example.com", "passcarlos", perfil3);
            cuentaRepository.save(cuenta3);

            // Nueva cuenta 4
            Perfil perfil4 = new Perfil("María López", 966663333, "Avenida Luna 101");
            perfilRepository.save(perfil4);
            Cuenta cuenta4 = new Cuenta("maria@example.com", "passmaria", perfil4);
            cuentaRepository.save(cuenta4);

            System.out.println("Datos iniciales de UtalEats Users cargados.");
        } else {
            System.out.println("Ya existen cuentas, no se cargaron datos iniciales.");
        }
    }
}