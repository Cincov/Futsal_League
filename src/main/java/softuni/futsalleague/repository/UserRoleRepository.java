package softuni.futsalleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.futsalleague.domein.entities.UserRoleEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findUserRoleEntityByRole(UserRoleEnums role);
}
