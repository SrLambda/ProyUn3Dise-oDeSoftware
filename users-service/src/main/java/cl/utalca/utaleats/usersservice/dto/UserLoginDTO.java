// users-service/src/main/java/cl/utalca/utaleats/usersservice/dto/UserLoginDTO.java
package cl.utalca.utaleats.usersservice.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank(message = "Username or email cannot be empty")
    private String usernameOrEmail;
    @NotBlank(message = "Password cannot be empty")
    private String password;

    // Constructor, Getters y Setters (como los que ya tienes)
    public UserLoginDTO() {}
    public UserLoginDTO(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }
    public String getUsernameOrEmail() { return usernameOrEmail; }
    public void setUsernameOrEmail(String usernameOrEmail) { this.usernameOrEmail = usernameOrEmail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}