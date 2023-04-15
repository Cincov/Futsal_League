package softuni.futsalleague.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.service.PlayerService;

import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {

    private PlayerService playerService;

    @Autowired
    public MarketController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/allPlayer")
    public String allPlayersForSale(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        List<PlayerViewModel> players = playerService.findAllPlayerForSale(userDetails.getUsername());
        model.addAttribute("players", players);

        return "market";
    }

    @GetMapping("/buy/{id}")
    public String buyPlayer(@PathVariable Long id, Model model) {

        PlayerViewModel player = playerService.findById(id);

        if(!model.containsAttribute("cannotBuy")) {
            model.addAttribute("cannotBuy", false);
        }

        model.addAttribute("player", player);

        return "buy-player";
    }

    @PostMapping("/buy/{id}")
    public String confirmBuyPlayer(@PathVariable Long id, Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {


       boolean successfullyBuy =  playerService.buyPlayer(id, userDetails.getUsername());

       if (!successfullyBuy) {
           model.addAttribute("cannotBuy", true);
       }

        return "redirect:/market/allPlayer";
    }
}
