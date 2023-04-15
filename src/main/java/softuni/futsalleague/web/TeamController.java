package softuni.futsalleague.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.domein.dtos.view.TeamViewModel;
import softuni.futsalleague.domein.enums.PlayerPosition;
import softuni.futsalleague.service.PlayerService;
import softuni.futsalleague.service.TeamService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;
    private static final String GOALKEEPER = String.valueOf(PlayerPosition.GOALKEEPER);
    private static final String DEFENDER = String.valueOf(PlayerPosition.DEFENDER);
    private static final String WING = String.valueOf(PlayerPosition.WINGER);
    private static final String FORWARD = String.valueOf(PlayerPosition.FORWARD);


    @Autowired
    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/myTeam")
    public String myTeam(Model model,
                         @AuthenticationPrincipal UserDetails userDetails) {

        TeamViewModel team = teamService.viewMyTeam(userDetails.getUsername());

        List<PlayerViewModel> players = teamService.findAllPlayers(userDetails.getUsername());

        List<PlayerViewModel> goalkeepers = new ArrayList<>();
        List<PlayerViewModel> defenders = new ArrayList<>();
        List<PlayerViewModel> wings = new ArrayList<>();
        List<PlayerViewModel> forwards = new ArrayList<>();
        for (PlayerViewModel player : players) {
            setArrays(goalkeepers, defenders, wings, forwards, player);
        }


        model.addAttribute("team", team);
        model.addAttribute("goalkeepers", goalkeepers);
        model.addAttribute("defenders", defenders);
        model.addAttribute("wings", wings);
        model.addAttribute("forwards",forwards);

        return "my-team";
    }

    @GetMapping("/table")
    public String table(Model model) {

        List<TeamViewModel> allTeams = teamService.findAllTeams();

        model.addAttribute("teams", allTeams);
        return "table";
    }

    @GetMapping("/{id}")
    public String teamDetails(@PathVariable Long id,
                              Model model) {

        TeamViewModel teamViewModel = teamService.findById(id);

        List<PlayerViewModel> teamGoalkeepers = new ArrayList<>();
        List<PlayerViewModel> teamDefenders = new ArrayList<>();
        List<PlayerViewModel> teamWings = new ArrayList<>();
        List<PlayerViewModel> teamForwards = new ArrayList<>();

        for (PlayerViewModel player : teamViewModel.getPlayers()) {
            setArrays(teamGoalkeepers, teamDefenders, teamWings, teamForwards, player);
        }


        model.addAttribute("team", teamViewModel);
        model.addAttribute("goalkeepers", teamGoalkeepers);
        model.addAttribute("defenders", teamDefenders);
        model.addAttribute("wings", teamWings);
        model.addAttribute("forwards",teamForwards);

        return "team-details";
    }



    private void setArrays(List<PlayerViewModel> teamGoalkeepers, List<PlayerViewModel> teamDefenders, List<PlayerViewModel> teamWings, List<PlayerViewModel> teamForwards, PlayerViewModel player) {
        String position = player.getPosition();

        if (position.equals(GOALKEEPER)) {
            teamGoalkeepers.add(player);
        } else if (position.equals(DEFENDER)) {
            teamDefenders.add(player);
        }else if (position.equals(WING)) {
            teamWings.add(player);
        }else if (position.equals(FORWARD)) {
            teamForwards.add(player);
        }
    }
}
