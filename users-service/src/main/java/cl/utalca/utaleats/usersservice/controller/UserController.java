package cl.utalca.utaleats.usersservice.controller;

import cl.utalca.utaleats.usersservice.dto.CuentaDTO;
import cl.utalca.utaleats.usersservice.dto.PerfilDTO;
import cl.utalca.utaleats.usersservice.dto.CuentaResponseDTO;
import cl.utalca.utaleats.usersservice.model.Cuenta;
import cl.utalca.utaleats.usersservice.model.Perfil;
import cl.utalca.utaleats.usersservice.service.UserService;
import jakarta.validation.Valid; // Importar para las validaciones
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users") // Una URL base común y limpia para el microservicio de usuarios
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (Frontend)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --- Endpoints de Creación y Autenticación ---

    /**
     * Registra una nueva cuenta de usuario.
     * HTTP POST a /api/users/register
     * @param cuentaDTO Datos de la cuenta a registrar.
     * @return Detalles de la cuenta creada o un error si el correo ya existe.
     */
    @PostMapping("/register")
    public ResponseEntity<CuentaResponseDTO> registerUser(@Valid @RequestBody CuentaDTO cuentaDTO) {
        try {
            Cuenta cuenta = userService.crearCuenta(cuentaDTO);
            // No se incluye el perfil en la respuesta inicial ya que se agrega después
            CuentaResponseDTO response = new CuentaResponseDTO(
                    cuenta.getId(),
                    cuenta.getCorreo(),
                    null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Manejo simple de error para casos de prueba
            return ResponseEntity.badRequest().body(new CuentaResponseDTO(null, e.getMessage(), null));
        }
    }

    /**
     * Inicia sesión para un usuario.
     * HTTP POST a /api/users/login
     * @param loginDTO Correo y contraseña para el inicio de sesión.
     * @return Detalles de la cuenta si el login es exitoso, o 401 Unauthorized.
     */
    @PostMapping("/login")
    public ResponseEntity<CuentaResponseDTO> login(@Valid @RequestBody CuentaDTO loginDTO) {
        return userService.login(loginDTO.getCorreo(), loginDTO.getContrasena())
                .map(cuenta -> {
                    // Si el login es exitoso, devuelve la cuenta completa (incluyendo perfil si existe)
                    CuentaResponseDTO response = new CuentaResponseDTO(
                            cuenta.getId(),
                            cuenta.getCorreo(),
                            cuenta.getPerfil()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CuentaResponseDTO(null, "Credenciales inválidas", null)));
    }

    /**
     * Obtiene los detalles de una cuenta por su ID.
     * HTTP GET a /api/users/{id}
     * @param id ID de la cuenta.
     * @return Detalles de la cuenta o 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> getAccountById(@PathVariable Long id) {
        return userService.obtenerCuentaPorId(id)
                .map(cuenta -> new CuentaResponseDTO(cuenta.getId(), cuenta.getCorreo(), cuenta.getPerfil()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene los detalles de una cuenta por su correo electrónico.
     * HTTP GET a /api/users/by-email?correo=...
     * @param correo Correo electrónico de la cuenta.
     * @return Detalles de la cuenta o 404 Not Found.
     */
    @GetMapping("/by-email")
    public ResponseEntity<CuentaResponseDTO> getAccountByEmail(@RequestParam String correo) {
        return userService.obtenerCuentaPorCorreo(correo)
                .map(cuenta -> new CuentaResponseDTO(cuenta.getId(), cuenta.getCorreo(), cuenta.getPerfil()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza la información de una cuenta existente.
     * HTTP PUT a /api/users/{id}
     * @param id ID de la cuenta a actualizar.
     * @param cuentaDTO Nuevos datos para la cuenta.
     * @return La cuenta actualizada o 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody CuentaDTO cuentaDTO) {
        try {
            Cuenta updatedCuenta = userService.actualizarCuenta(id, cuentaDTO);
            return ResponseEntity.ok(new CuentaResponseDTO(updatedCuenta.getId(), updatedCuenta.getCorreo(), updatedCuenta.getPerfil()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una cuenta y su perfil asociado por ID.
     * HTTP DELETE a /api/users/{id}
     * @param id ID de la cuenta a eliminar.
     * @return 204 No Content si se elimina, o 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            userService.eliminarCuenta(id);
            return ResponseEntity.noContent().build(); // 204 No Content para eliminación exitosa
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Endpoints para la Gestión de Perfiles (asociados a Cuentas) ---

    /**
     * Agrega un perfil a una cuenta existente.
     * HTTP POST a /api/users/{accountId}/profile
     * @param accountId ID de la cuenta a la que se le agregará el perfil.
     * @param perfilDTO Datos del perfil a agregar.
     * @return La cuenta con su nuevo perfil o un error si la cuenta no existe o ya tiene perfil.
     */
    @PostMapping("/{accountId}/profile")
    public ResponseEntity<CuentaResponseDTO> addProfileToAccount(@PathVariable Long accountId, @Valid @RequestBody PerfilDTO perfilDTO) {
        try {
            userService.agregarPerfil(accountId, perfilDTO);
            // Después de agregar el perfil, obtenemos la cuenta completa para la respuesta
            Cuenta cuentaConPerfil = userService.obtenerCuentaPorId(accountId).orElseThrow();
            return ResponseEntity.ok(new CuentaResponseDTO(cuentaConPerfil.getId(), cuentaConPerfil.getCorreo(), cuentaConPerfil.getPerfil()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new CuentaResponseDTO(null, e.getMessage(), null));
        }
    }

    /**
     * Actualiza el perfil de una cuenta existente.
     * HTTP PUT a /api/users/{accountId}/profile
     * @param accountId ID de la cuenta cuyo perfil se actualizará.
     * @param perfilDTO Nuevos datos para el perfil.
     * @return La cuenta con su perfil actualizado o un error.
     */
    @PutMapping("/{accountId}/profile")
    public ResponseEntity<CuentaResponseDTO> updateProfile(@PathVariable Long accountId, @Valid @RequestBody PerfilDTO perfilDTO) {
        try {
            userService.actualizarPerfil(accountId, perfilDTO);
            // Después de actualizar el perfil, obtenemos la cuenta completa para la respuesta
            Cuenta cuentaConPerfil = userService.obtenerCuentaPorId(accountId).orElseThrow();
            return ResponseEntity.ok(new CuentaResponseDTO(cuentaConPerfil.getId(), cuentaConPerfil.getCorreo(), cuentaConPerfil.getPerfil()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping // Este endpoint responderá a GET /api/users
    public ResponseEntity<List<CuentaResponseDTO>> getAllUsers() {
        List<Cuenta> cuentas = userService.getAllCuentas();
        // Mapea las cuentas a CuentaResponseDTOs (omitiendo contraseñas, etc.)
        List<CuentaResponseDTO> responseDTOs = cuentas.stream()
                .map(cuenta -> new CuentaResponseDTO(cuenta.getId(), cuenta.getCorreo(), cuenta.getPerfil()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}