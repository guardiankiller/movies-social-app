package bg.guardiankiller.moviessocialapp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
    @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters!")
    private String username;

    @Size(min = 5, max = 36, message = "Name length must be between 2 and 20 characters!")
    private String fullName;

    @NotBlank(message = "Email cannot be empty!")
    @Email
    @Size(max = 40, message = "Email length cannot be more than 40 characters")
    private String email;

    @Size(min = 5, max = 50, message = "Password length must be between 5 and 50 characters!")
    private String password;

    @Size(min = 5, max = 50, message = "Password length must be between 5 and 50 characters!")
    private String confirmPassword;

    public UserRegisterDTO() {
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
