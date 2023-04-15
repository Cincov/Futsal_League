package softuni.futsalleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.futsalleague.domein.entities.CoachEntity;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long> {


}
