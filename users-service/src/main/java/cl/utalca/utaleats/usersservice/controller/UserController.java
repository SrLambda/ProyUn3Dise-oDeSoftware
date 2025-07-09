// users-service/src/main/java/cl/utalca/utaleats/usersservice/controller/UserController.java
package cl.utalca.utaleats.usersservice.controller;

import cl.utalca.utaleats.usersservice.dto.UserLoginDTO;
import cl.utalca.utaleats.usersservice.dto.UserRegistrationDTO;
import cl.utalca.utaleats.usersservice.dto.UserResponseDTO;
import cl.utalca.utaleats.usersservice.model.User;
import cl.utalca.utaleats.usersservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen para desarrollo.
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            User newUser = userService.registerNewUser(registrationDTO);
            return new ResponseEntity<>(new UserResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getEmail()), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO loginDTO) {
        Optional<User> authenticatedUser = userService.authenticateUser(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()
        );

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();
            return new ResponseEntity<>(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            return new ResponseEntity<>(new UserResponseDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getEmail()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
