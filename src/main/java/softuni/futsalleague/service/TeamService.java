package softuni.futsalleague.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.domein.dtos.view.TeamViewModel;
import softuni.futsalleague.domein.entities.CoachEntity;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

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
        TeamEntity teamEntity = teamRepository.findById(id).orElse(null);
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



    public List<TeamViewModel> findAllTeams() {

        List<TeamViewModel> orderTeams = new ArrayList<>();
        List<TeamEntity> allTeams = teamRepository.findAllTeams();
        for (TeamEntity teamEntity : allTeams) {
            TeamViewModel teamViewModel = mapTeamViewModel(teamEntity);
            orderTeams.add(teamViewModel);
        }

        return orderTeams;
    }

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

    public void updatePlayersList(String username, PlayerEntity player) {
        TeamEntity team = teamRepository.findByUser_Email(username);

        List<PlayerEntity> players = team.getPlayers();
        players.add(player);
        team.setPlayers(players);
        team.setRating(setTeamRating(team.getPlayers(), team.getCoachEntity()));
        teamRepository.saveAndFlush(team);
    }
}
