package softuni.futsalleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.entities.TeamEntity;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "teams")
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    TeamEntity findByUser_Email(String username);

    Optional<TeamEntity> findByUser_Id(Long id);

    Optional<TeamEntity> findTeamEntityByName(String name);


    Optional<TeamEntity> findTeamEntityByPlayersContains(PlayerEntity player);

    @Query("SELECT t from TeamEntity t WHERE t.name != 'Adminite' order by t.rating DESC")
    List<TeamEntity> findAllTeams();

    @Query("SELECT t from TeamEntity t WHERE t.name != 'Adminite' order by t.rating DESC limit 3")
      List<TeamEntity> findTopThreeTeams();
}
