package bg.guardiankiller.moviessocialapp.model.entity;

import bg.guardiankiller.moviessocialapp.model.UserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private UserRoles name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public RoleEntity() {
    }
}
