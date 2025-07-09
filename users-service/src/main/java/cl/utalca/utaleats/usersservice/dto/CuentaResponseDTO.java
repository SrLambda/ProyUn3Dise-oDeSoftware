package cl.utalca.utaleats.usersservice.dto;

import cl.utalca.utaleats.usersservice.model.Perfil;

public class CuentaResponseDTO {

    private Long id;
    private String correo;
    private Perfil perfil; // puedes usar PerfilDTO si prefieres

    public CuentaResponseDTO() {}

    public CuentaResponseDTO(Long id, String correo, Perfil perfil) {
        this.id = id;
        this.correo = correo;
        this.perfil = perfil;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
