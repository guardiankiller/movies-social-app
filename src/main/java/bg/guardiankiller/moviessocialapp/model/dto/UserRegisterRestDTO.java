package bg.guardiankiller.moviessocialapp.model.dto;

import bg.guardiankiller.moviessocialapp.validation.groups.Group1;
import bg.guardiankiller.moviessocialapp.validation.groups.Group2;
import bg.guardiankiller.moviessocialapp.validation.groups.Group3;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterRestDTO {

    @NotBlank(message = "Username cannot be empty!", groups = Group1.class)
    @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters!", groups = Group2.class)
    private String username;

    @NotBlank(message = "Full name cannot be empty!", groups = Group1.class)
    @Size(min = 5, max = 36, message = "Name length must be between 2 and 20 characters!", groups = Group2.class)
    private String fullName;

    @NotBlank(message = "Email cannot be empty!", groups = Group1.class)
    @Email(message = "Email is not valid", groups = Group3.class)
    @Size(max = 40, message = "Email length cannot be more than 40 characters", groups = Group2.class)
    private String email;

    @NotBlank(message = "Password cannot be empty!", groups = Group1.class)
    @Size(min = 5, max = 50, message = "Password length must be between 5 and 50 characters!", groups = Group2.class)
    private String password;

    @NotBlank(message = "Password cannot be empty!", groups = Group1.class)
    @Size(min = 5, max = 50, message = "Password length must be between 5 and 50 characters!", groups = Group2.class)
    private String confirmPassword;

    public UserRegisterRestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
