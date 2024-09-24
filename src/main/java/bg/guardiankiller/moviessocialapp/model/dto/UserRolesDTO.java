package bg.guardiankiller.moviessocialapp.model.dto;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRolesDTO {
    private UserRoles[] roles;
}
