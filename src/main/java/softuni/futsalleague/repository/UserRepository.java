package softuni.futsalleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.futsalleague.domein.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByEmail(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.email != 'admin@example.com'")
    List<UserEntity> findAllWithoutAdminov();

    Optional<UserEntity> findUserEntityByUsername(String username);

}
