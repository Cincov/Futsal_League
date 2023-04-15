package softuni.futsalleague.domein.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import softuni.futsalleague.domein.enums.UserRoleEnums;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnums role;

    public UserRoleEnums getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnums role) {
        this.role = role;
        return this;
    }
}
