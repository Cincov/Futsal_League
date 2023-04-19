package softuni.futsalleague.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.service.PlayerService;

@RestController
@RequestMapping("/api/players")
public class RESTPlayersController {

    private final PlayerService playerService;

    @Autowired
    public RESTPlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerViewModel> getGuests(@PathVariable("id")Long id){
        return ResponseEntity.ok(this.playerService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePlayer(@PathVariable("id") Long id){

        this.playerService.deletePlayer(id);

        return ResponseEntity.ok(null);
    }
}
