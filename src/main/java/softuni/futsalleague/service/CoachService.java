package softuni.futsalleague.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.futsalleague.domein.dtos.binding.CoachCreateFormDto;
import softuni.futsalleague.domein.entities.CoachEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.repository.CoachRepository;
import softuni.futsalleague.repository.TeamRepository;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    @Autowired
    public CoachService(CoachRepository coachRepository, TeamRepository teamRepository, TeamService teamService) {
        this.coachRepository = coachRepository;
        this.teamRepository = teamRepository;
        this.teamService = teamService;
    }

    public boolean createCoach(CoachCreateFormDto coachCreateFormDto, String username) {
        TeamEntity byUserEmail = teamRepository.findByUser_Email(username);
        if (byUserEmail.getCoachEntity() != null) {
            return false;
        }

        CoachEntity coach = new CoachEntity();
        coach.setFirstName(coachCreateFormDto.getFirstName())
                .setLastName(coachCreateFormDto.getLastName())
                .setAge(coachCreateFormDto.getAge());

        coach.setTechnique(setStats())
                .setPhysical(setStats())
                .setTactical(setStats())
                .setTeamWork(setStats())
                .setRating(setRating(coach.getTactical(), coach.getPhysical(),
                        coach.getTechnique(), coach.getTeamWork()));

        coachRepository.save(coach);

        teamService.updateTeam(username, coach);

        return true;
    }

    private static int setStats() {
        return (int) (Math.random() * (99 - 50)) + 50;
    }

    private static int setRating(int tactical, int technique, int teamWork, int physical) {
        return (tactical + technique + technique + physical) / 4;
    }
}
