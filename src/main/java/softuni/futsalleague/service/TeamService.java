package softuni.futsalleague.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.domein.dtos.view.TeamViewModel;
import softuni.futsalleague.domein.entities.CoachEntity;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.exeption.ObjectNotFoundException;
import softuni.futsalleague.repository.TeamRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.intValue;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public TeamViewModel viewMyTeam(String username) {

        TeamEntity team = teamRepository.findByUser_Email(username);
        TeamViewModel teamViewModel = new TeamViewModel();
        teamViewModel.setId(team.getId())
                .setName(team.getName())
                .setBudget(team.getBudget());


        if (team.getCoachEntity() == null) {
            teamViewModel.setCoachName("N/A");
            teamViewModel.setCoachRating(0);
        } else {
            teamViewModel.setCoachName(team.getCoachEntity().getFirstName() + " " + team.getCoachEntity().getLastName());
            teamViewModel.setCoachRating(team.getCoachEntity().getRating());
            teamViewModel.setRating(setTeamRating(team.getPlayers(), team.getCoachEntity()));
        }

        return teamViewModel;

    }

    public TeamViewModel findById(Long id) {
        TeamEntity teamEntity = teamRepository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException("Team not found"));
        return mapTeamViewModel(teamEntity);
    }

    public List<PlayerViewModel> findAllPlayers(String username) {
        TeamEntity teamEntity = teamRepository.findByUser_Email(username);
        List<PlayerEntity> players = teamEntity.getPlayers();
        List<PlayerViewModel> playerViewModels = new ArrayList<>();

        players.forEach(playerEntity -> {
            PlayerViewModel playerViewModel = mapPlayerViewModel(playerEntity);
            playerViewModels.add(playerViewModel);
        });

        return playerViewModels;
    }

    @Transactional
    public List<TeamViewModel> findAllTeams() {

        List<TeamViewModel> orderTeams = new ArrayList<>();
        List<TeamEntity> allTeams = teamRepository.findAllTeams();
        for (TeamEntity teamEntity : allTeams) {
            TeamViewModel teamViewModel = mapTeamViewModel(teamEntity);
            orderTeams.add(teamViewModel);
        }

        return orderTeams;
    }

    @Transactional
    public void updateTeam(String username, CoachEntity coach) {
        TeamEntity team = teamRepository.findByUser_Email(username);

        team.setCoachEntity(coach);
        team.setRating(setTeamRating(team.getPlayers(), team.getCoachEntity()));

        teamRepository.saveAndFlush(team);
    }

    private PlayerViewModel mapPlayerViewModel(PlayerEntity player) {
        PlayerViewModel playerViewModel = new PlayerViewModel();
        playerViewModel.setId(player.getId())
                .setFirstName(player.getFirstName())
                .setLastName(player.getLastName())
                .setPosition(player.getPosition().name())
                .setRating(player.getRating())
                .setPace(player.getPace())
                .setShooting(player.getShooting())
                .setDefending(player.getDefending())
                .setDribbling(player.getDribbling())
                .setPassing(player.getPassing());

        return playerViewModel;
    }


    private List<PlayerViewModel> mapPlayers(List<PlayerEntity> players) {
        List<PlayerViewModel> playersViewModel = new ArrayList<>();

        for (PlayerEntity player : players) {

            PlayerViewModel playerViewModel = mapPlayerViewModel(player);
            playersViewModel.add(playerViewModel);
        }
        return playersViewModel;
    }

    private TeamViewModel mapTeamViewModel(TeamEntity teamEntity) {
        TeamViewModel teamViewModel = new TeamViewModel();
        teamViewModel.setId(teamEntity.getId())
                .setName(teamEntity.getName())
                .setBudget(teamEntity.getBudget())
                .setRating(teamEntity.getRating())
                .setPlayers(mapPlayers(teamEntity.getPlayers()));

        if (teamEntity.getCoachEntity() == null) {
            teamViewModel.setCoachName("N/A");
        } else {
            teamViewModel.setCoachName(teamEntity.getCoachEntity().getFirstName() + " " + teamEntity.getCoachEntity().getLastName());
        }
        return teamViewModel;
    }

    private int setTeamRating(List<PlayerEntity> players, CoachEntity coach) {
        if (!players.isEmpty() && coach != null) {
            int sumPlayersRating = players.stream().mapToInt(PlayerEntity::getRating).sum();
            return ((sumPlayersRating + coach.getRating()) / (players.size() + 1));
        } else {
            return 0;
        }
    }

    @Transactional
    public void updatePlayersList(String teamName, PlayerEntity player) {
        TeamEntity team = teamRepository.findTeamEntityByName(teamName).
                orElseThrow(() -> new ObjectNotFoundException("Team not found"));;

        List<PlayerEntity> players = team.getPlayers();
        players.add(player);
        team.setPlayers(players);
        team.setRating(setTeamRating(team.getPlayers(), team.getCoachEntity()));
        teamRepository.save(team);
    }

    @Transactional
    public void updatePlayersListToSale(String teamName, PlayerEntity player) {
        TeamEntity team = teamRepository.findTeamEntityByName(teamName).
                orElseThrow(() -> new ObjectNotFoundException("Team not found"));

        List<PlayerEntity> players = team.getPlayers();
        players.remove(player);
        team.setPlayers(players);
        team.setRating(setTeamRating(team.getPlayers(), team.getCoachEntity()));
        teamRepository.saveAndFlush(team);
    }

    public List<TeamViewModel> getTopThreeTeams() {
        List<TeamViewModel> list = new ArrayList<>();
        teamRepository.findTopThreeTeams().forEach(teamEntity -> {
            TeamViewModel teamViewModel = mapTeamViewModel(teamEntity);
            list.add(teamViewModel);
        });

        return list;
    }

    public void decreaseTeamsBudget() {
        List<TeamEntity> teams = teamRepository.findAllTeams();
        teams.forEach(teamEntity -> {
            BigDecimal teamBudget = teamEntity.getBudget().subtract(BigDecimal.valueOf(150));
            teamEntity.setBudget(teamBudget);

            if (intValue(teamBudget) <= 500) {
                teamEntity.setBudget(BigDecimal.valueOf(500));
            }
            teamRepository.saveAndFlush(teamEntity);
        });

    }
}
