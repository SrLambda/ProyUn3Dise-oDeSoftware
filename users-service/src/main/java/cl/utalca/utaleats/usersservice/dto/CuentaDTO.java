package cl.utalca.utaleats.usersservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CuentaDTO {

    @Email(message = "Debe ser un correo válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    public CuentaDTO() {}

    public CuentaDTO(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
