package softuni.futsalleague.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.futsalleague.domein.dtos.binding.PlayerCreateFormDto;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.service.PlayerService;
import softuni.futsalleague.service.TeamService;


@Controller
@RequestMapping("/players")
public class PlayerController {


    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    private final PlayerService playerService;
    private final TeamService teamService;

    @Autowired
    public PlayerController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }


    @ModelAttribute(name = "playerCreateFormDto")
    public PlayerCreateFormDto playerCreateFormDto() {
        return new PlayerCreateFormDto();
    }

    @GetMapping("/add")
    public String createPlayer(Model model) {

        if (!model.containsAttribute("maxPlayer")) {
            model.addAttribute("maxPlayer", false);
        }

        return "create-player";
    }

    @PostMapping("/add")
    public String confirmCreatePLayer(@Valid PlayerCreateFormDto playerCreateFormDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("playerCreateFormDto", playerCreateFormDto)
                    .addFlashAttribute(BINDING_RESULT_PATH + "playerCreateFormDto", bindingResult);

            return "redirect:/players/add";
        }

        boolean isCreated = playerService.createPlayer(playerCreateFormDto, userDetails.getUsername());

        if ( !isCreated) {
            redirectAttributes. addFlashAttribute("maxPlayer", true);
            return "redirect:/players/add";
        }

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String playerDetails(@PathVariable Long id,
                                Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {

       PlayerViewModel player = playerService.findById(id);
       if (player.getTeamName().equals(teamService.viewMyTeam(userDetails.getUsername()).getName())) {
           model.addAttribute("myPlayer", true);
       }

       model.addAttribute("player", player);
        return "player-details";
    }

    @PostMapping("/{id}")
    public String sendToMarketPlayer(@PathVariable Long id) {

        playerService.sendToMarket(id);
        return "redirect:/teams/myTeam";
    }

    @PostMapping("/quick-sale/{id}")
    public String quickSalePlayer(@PathVariable Long id,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        playerService.quickSale(id, userDetails.getUsername());

        return "redirect:/teams/myTeam";
    }
}
