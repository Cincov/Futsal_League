package softuni.futsalleague.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.futsalleague.domein.dtos.view.TeamViewModel;
import softuni.futsalleague.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class RESTTeamController {

    private final TeamService teamService;

    public RESTTeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/topThree")
    public ResponseEntity<List<TeamViewModel>> getTopThreeTeams() {

        return ResponseEntity.ok(this.teamService.getTopThreeTeams());
    }
}
