package softuni.futsalleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.enums.PlayerPosition;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    List<PlayerEntity> findByPosition(PlayerPosition position);

    @Query("SELECT p FROM PlayerEntity p where p.isSale = true and p.teamEntity.user.email != ?1")
    List<PlayerEntity> findPlayerEntitiesBySale(String username);

//    Optional<TeamEntity> findPlayerEntityByTeamEntity();

    @Query("SELECT p FROM PlayerEntity  p where p.position = ?1")
    List<PlayerEntity> findPlayerEntitiesByPosition(PlayerPosition position);

    void deleteAllByTeamEntity_Id(Long id);

    List<PlayerEntity> findAllByTeamEntity_Id(Long id);
}
