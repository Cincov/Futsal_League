package softuni.futsalleague.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.futsalleague.service.PlayerService;
import softuni.futsalleague.service.TeamService;

@Component
public class ScheduleTasks {

    private final PlayerService playerService;
    private final TeamService teamService;

    @Autowired
    public ScheduleTasks(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @Scheduled(cron = "30 9 * * * MON-FRI")
    public void decreaseAllPlayersPrice() {
        playerService.decreasePlayersPrice();
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void decreaseTeamsBudget() {
        teamService.decreaseTeamsBudget();
    }
}
