package softuni.futsalleague.domein.dtos.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;

@AllArgsConstructor
@Getter
public class UserWithRoleDTO {
    private Long id;
    private String email;
    private String username;
    private boolean isAdmin;

    public UserWithRoleDTO(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.isAdmin = user.getRoles().
                stream().
                anyMatch(r -> r.getRole().equals(UserRoleEnums.ADMIN));
    }
}
