package bg.guardiankiller.moviessocialapp.model.dto;

import bg.guardiankiller.moviessocialapp.validation.groups.Group2;
import bg.guardiankiller.moviessocialapp.validation.groups.Group3;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserChangeDTO {

    @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters!", groups = Group2.class)
    private String username;

    @Size(min = 5, max = 36, message = "Name length must be between 2 and 20 characters!", groups = Group2.class)
    private String fullName;

    @Email(message = "Email is not valid", groups = Group3.class)
    @Size(max = 40, message = "Email length cannot be more than 40 characters", groups = Group2.class)
    private String email;

    @Size(min = 5, max = 50, message = "Password length must be between 5 and 50 characters!", groups = Group2.class)
    private String password;

    @Size(min = 5, max = 50, message = "Password length must be between 5 and 50 characters!", groups = Group2.class)
    private String confirmPassword;
}
