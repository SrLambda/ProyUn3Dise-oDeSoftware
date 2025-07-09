package cl.utalca.utaleats.usersservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PerfilDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El teléfono es obligatorio")
    private Integer telefono;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    public PerfilDTO() {}

    public PerfilDTO(String nombre, Integer telefono, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
