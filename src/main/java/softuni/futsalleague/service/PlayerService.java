package softuni.futsalleague.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.futsalleague.domein.dtos.binding.PlayerCreateFormDto;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.enums.PlayerPosition;
import softuni.futsalleague.repository.PlayerRepository;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.aspectj.runtime.internal.Conversions.intValue;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, UserRepository userRepository,
                         TeamRepository teamRepository, TeamService teamService, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamService = teamService;
        this.modelMapper = modelMapper;
    }

    public boolean createPlayer(PlayerCreateFormDto playerCreateFormDto, String username) {

        TeamEntity team = teamRepository.findByUser_Email(username);

        List<PlayerEntity> playerOnSamePosition = new ArrayList<>();

        team.getPlayers().forEach(player -> {
            if (player.getPosition().name().equals(playerCreateFormDto.getPosition())) {
                playerOnSamePosition.add(player);
            }
        });

        if (team.getPlayers().size() == 15) {
            return false;
        }

        if (playerOnSamePosition.size() == 3) {
            return false;
        }

        PlayerEntity player = new PlayerEntity();
        player.setFirstName(playerCreateFormDto.getFirstName())
                .setLastName(playerCreateFormDto.getLastName())
                .setAge(playerCreateFormDto.getAge());

        player.setSale(false);

        player.setPosition(PlayerPosition.valueOf(playerCreateFormDto.getPosition()));

        player.setPace(setStats())
                .setShooting(setStats())
                .setDribbling(setStats())
                .setDefending(setStats())
                .setPassing(setStats());

        player.setRating(setRating(player.getPace(), player.getShooting(), player.getDribbling(),
                player.getDefending(), player.getPassing()));
        player.setTeamEntity(teamRepository.findByUser_Email(username));
        player.setPrice(setPlayerPrice(player));
        playerRepository.save(player);
        teamService.updatePlayersList(username, player);

        return true;
    }

    private BigDecimal setPlayerPrice(PlayerEntity player) {
        int sum = 0;
        int rating = player.getRating();

        if (rating >= 50 && rating < 60) {
            sum = 1000;
        } else if (rating >= 60 && rating < 70) {
            sum = 1500;
        } else if (rating >= 70 && rating < 80) {
            sum = 2000;
        } else if (rating >= 80 && rating < 90) {
            sum = 2500;
        } else if (rating >= 90 && rating < 100) {
            sum = 3000;
        }

        return new BigDecimal(sum);
    }

    public void sendToMarket(Long id) {
        PlayerEntity player = playerRepository.findById(id).orElse(null);
        player.setSale(true);
        playerRepository.save(player);
    }

    public List<PlayerViewModel> findAllPlayerForSale(String username) {

        List<PlayerEntity> playerEntitiesBySale = playerRepository.findPlayerEntitiesBySale(username);
        List<PlayerViewModel> playerViewModels = new ArrayList<>();

        for (PlayerEntity player : playerEntitiesBySale) {
            PlayerViewModel playerViewModel = modelMapper.map(player, PlayerViewModel.class);
            playerViewModels.add(playerViewModel);
        }
        return playerViewModels;
    }


    @Transactional
    public boolean buyPlayer(Long id, String username) {
        PlayerEntity player = playerRepository.findById(id).orElse(null);

        TeamEntity playerTeam = teamRepository.findTeamEntityByName(player.getTeamEntity().getName()).orElse(null);

        TeamEntity buyerTeam = teamRepository.findByUser_Email(username);

        if (playerTeam.getPlayers().size() == 15 ||
                playerRepository.findPlayerEntitiesByPosition(player.getPosition()).size() == 3) {
            return false;
        }


        int budget = intValue(buyerTeam.getBudget());
        int price = intValue(player.getPrice());
        int playerTeamBudget = intValue(playerTeam.getBudget());

        if (budget < price) {
            return false;
        }

        List<PlayerEntity> buyerTeamPlayers = buyerTeam.getPlayers();
        List<PlayerEntity> playerTeamPlayers = playerTeam.getPlayers();
        playerTeamPlayers.remove(player);
        buyerTeamPlayers.add(player);


        player.setTeamEntity(buyerTeam);
        buyerTeam.setPlayers(buyerTeamPlayers);
        playerTeam.setPlayers(playerTeamPlayers);
        player.setSale(false);


        buyerTeam.setBudget(BigDecimal.valueOf(budget - price));
        playerTeam.setBudget(BigDecimal.valueOf(playerTeamBudget + price));



        teamRepository.saveAndFlush(buyerTeam);
        teamRepository.saveAndFlush(playerTeam);
        playerRepository.saveAndFlush(player);

        return true;
    }

    public void quickSale(Long id, String username) {

        TeamEntity team = teamRepository.findByUser_Email(username);
        int budget = intValue(team.getBudget()) + 500;
        team.setBudget(BigDecimal.valueOf(budget));
        PlayerEntity player = playerRepository.findById(id).orElse(null);
        List<PlayerEntity> players = team.getPlayers();
        players.remove(player);
        team.setPlayers(players);

        teamRepository.saveAndFlush(team);
        playerRepository.deleteById(id);

    }

    private int setStats() {
        return (int) (Math.random() * (99 - 50)) + 50;
    }

    private int setRating(int pace, int shooting, int defending, int dribbling, int passing) {
        return (pace + shooting + defending + dribbling + passing) / 5;
    }

    public PlayerViewModel findById(Long id) {
        Optional<PlayerEntity> player = playerRepository.findById(id);
        PlayerViewModel playerViewModel = playerRepository.findById(id)
                .map(playerEntity -> modelMapper.map(playerEntity, PlayerViewModel.class))
                .orElse(null);

        playerViewModel.setTeamName(player.get().getTeamEntity().getName());
        return playerViewModel;
    }

}



