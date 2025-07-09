package cl.utalca.utaleats.usersservice.service;

import cl.utalca.utaleats.usersservice.dto.CuentaDTO;
import cl.utalca.utaleats.usersservice.dto.PerfilDTO;
import cl.utalca.utaleats.usersservice.model.Cuenta;
import cl.utalca.utaleats.usersservice.model.Perfil;
import cl.utalca.utaleats.usersservice.repository.CuentaRepository;
import cl.utalca.utaleats.usersservice.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar para transacciones

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final CuentaRepository cuentaRepository;
    private final PerfilRepository perfilRepository;

    public UserService(CuentaRepository cuentaRepository, PerfilRepository perfilRepository) {
        this.cuentaRepository = cuentaRepository;
        this.perfilRepository = perfilRepository;
    }

    /**
     * Crea una nueva cuenta de usuario.
     * @param cuentaDTO Datos de la cuenta (correo y contraseña).
     * @return La Cuenta creada.
     * @throws RuntimeException si el correo ya está registrado.
     */
    @Transactional
    public Cuenta crearCuenta(CuentaDTO cuentaDTO) {
        if (cuentaRepository.findByCorreo(cuentaDTO.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }
        Cuenta cuenta = new Cuenta();
        cuenta.setCorreo(cuentaDTO.getCorreo());
        cuenta.setContrasena(cuentaDTO.getContrasena()); // Contraseña en texto plano para casos de prueba
        return cuentaRepository.save(cuenta);
    }

    /**
     * Asocia un perfil a una cuenta existente.
     * @param cuentaId ID de la cuenta a la que se le agregará el perfil.
     * @param perfilDTO Datos del perfil (nombre, teléfono, dirección).
     * @return El Perfil creado y asociado.
     * @throws RuntimeException si la cuenta no se encuentra o ya tiene un perfil.
     */
    @Transactional
    public Perfil agregarPerfil(Long cuentaId, PerfilDTO perfilDTO) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + cuentaId));

        if (cuenta.getPerfil() != null) {
            throw new RuntimeException("La cuenta ya tiene un perfil asociado.");
        }

        Perfil perfil = new Perfil();
        perfil.setNombre(perfilDTO.getNombre());
        perfil.setTelefono(perfilDTO.getTelefono());
        perfil.setDireccion(perfilDTO.getDireccion());

        Perfil perfilGuardado = perfilRepository.save(perfil);

        cuenta.setPerfil(perfilGuardado);
        cuentaRepository.save(cuenta);

        return perfilGuardado;
    }

    /**
     * Busca una cuenta por su ID.
     * @param id ID de la cuenta.
     * @return Un Optional que contiene la Cuenta si se encuentra.
     */
    public Optional<Cuenta> obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    /**
     * Busca una cuenta por su correo electrónico.
     * @param correo Correo electrónico de la cuenta.
     * @return Un Optional que contiene la Cuenta si se encuentra.
     */
    public Optional<Cuenta> obtenerCuentaPorCorreo(String correo) {
        return cuentaRepository.findByCorreo(correo);
    }

    /**
     * Intenta iniciar sesión con un correo y contraseña.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @return Un Optional que contiene la Cuenta si las credenciales son válidas.
     */
    public Optional<Cuenta> login(String correo, String contrasena) {
        return cuentaRepository.findByCorreo(correo)
                .filter(cuenta -> cuenta.getContrasena().equals(contrasena)); // Comparación directa
    }

    /**
     * Actualiza la información de una cuenta existente (correo, y opcionalmente contraseña).
     * @param id ID de la cuenta a actualizar.
     * @param cuentaDTO Nuevos datos de la cuenta.
     * @return La Cuenta actualizada.
     * @throws RuntimeException si la cuenta no se encuentra.
     */
    @Transactional
    public Cuenta actualizarCuenta(Long id, CuentaDTO cuentaDTO) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        cuentaExistente.setCorreo(cuentaDTO.getCorreo());
        // Solo actualizar contraseña si se proporciona una nueva y no está vacía
        if (cuentaDTO.getContrasena() != null && !cuentaDTO.getContrasena().isEmpty()) {
            cuentaExistente.setContrasena(cuentaDTO.getContrasena());
        }
        return cuentaRepository.save(cuentaExistente);
    }

    /**
     * Actualiza la información del perfil asociado a una cuenta.
     * @param cuentaId ID de la cuenta cuyo perfil se actualizará.
     * @param perfilDTO Nuevos datos del perfil.
     * @return El Perfil actualizado.
     * @throws RuntimeException si la cuenta no se encuentra o no tiene perfil.
     */
    @Transactional
    public Perfil actualizarPerfil(Long cuentaId, PerfilDTO perfilDTO) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + cuentaId));

        Perfil perfilExistente = cuenta.getPerfil();
        if (perfilExistente == null) {
            throw new RuntimeException("La cuenta no tiene un perfil asociado.");
        }

        perfilExistente.setNombre(perfilDTO.getNombre());
        perfilExistente.setTelefono(perfilDTO.getTelefono());
        perfilExistente.setDireccion(perfilDTO.getDireccion());
        return perfilRepository.save(perfilExistente);
    }

    /**
     * Elimina una cuenta y su perfil asociado por ID.
     * @param id ID de la cuenta a eliminar.
     * @throws RuntimeException si la cuenta no se encuentra.
     */
    @Transactional
    public void eliminarCuenta(Long id) {
        // Debido a CascadeType.ALL en la relación OneToOne, el perfil asociado también se eliminará.
        cuentaRepository.deleteById(id);
    }

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }
}