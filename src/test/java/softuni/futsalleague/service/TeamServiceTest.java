package softuni.futsalleague.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.domein.dtos.view.TeamViewModel;
import softuni.futsalleague.domein.entities.CoachEntity;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.enums.PlayerPosition;
import softuni.futsalleague.repository.TeamRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @Captor
    private ArgumentCaptor<TeamEntity> teamEntityArgumentCaptor;

    private TeamService toTest;


    @BeforeEach
    void setUp() {
        toTest = new TeamService(mockTeamRepository, mockModelMapper);


    }

    @Test
    void testViewMyTeam() {

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        when(mockTeamRepository.findByUser_Email("username")).thenReturn(team);
        TeamViewModel teamViewModel = toTest.viewMyTeam("username");

        Assertions.assertEquals(team.getName(), teamViewModel.getName());

    }

    @Test
    void testFindById() {

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        when(mockTeamRepository.findById(1L)).thenReturn(Optional.of(team));
        TeamViewModel teamViewModel = toTest.findById(1L);

        Assertions.assertEquals(team.getName(), teamViewModel.getName());
    }

    @Test
    void testFindAllPlayers() {

        List<PlayerEntity> players = new ArrayList<>();
        PlayerEntity player1 = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player1.setRating(77).setDefending(55).setPosition(PlayerPosition.DEFENDER);
        PlayerEntity player2 = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player2.setRating(77).setDefending(55).setPosition(PlayerPosition.WINGER);

        players.add(player1);
        players.add(player2);

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team1")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team.setPlayers(players).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        when(mockTeamRepository.findByUser_Email("pepi")).thenReturn(team);

        List<PlayerViewModel> allPlayers = toTest.findAllPlayers("pepi");

        Assertions.assertEquals(player1.getRating(), allPlayers.get(0).getRating());

    }

    @Test
    void testFindAllTeams() {

        TeamEntity team1 = new TeamEntity();
        team1.setId(1L);
        team1.setName("Team1")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team1.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        TeamEntity team2 = new TeamEntity();
        team2.setId(1L);
        team2.setName("Team2")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team2.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));


        when(mockTeamRepository.findAllTeams()).thenReturn(List.of(team1, team2));

        List<TeamViewModel> allTeams = toTest.findAllTeams();

        Assertions.assertEquals(team1.getName(), allTeams.get(0).getName());
        Assertions.assertEquals(team2.getRating(), allTeams.get(1).getRating());
    }

    @Test
    void testUpdateTeam() {
        CoachEntity coach = new CoachEntity().setRating(66);
        coach.setFirstName("asd").setLastName("asd").setAge(33);

        TeamEntity team2 = new TeamEntity();
        team2.setId(1L);
        team2.setName("Team2")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team2.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        when(mockTeamRepository.findByUser_Email("username")).thenReturn(team2);

        toTest.updateTeam("username", coach);

        verify(mockTeamRepository).saveAndFlush(teamEntityArgumentCaptor.capture());

        TeamEntity capture = teamEntityArgumentCaptor.getValue();
        Assertions.assertEquals(coach.getAge(), capture.getCoachEntity().getAge());

    }

    @Test
    void testUpdatePlayersList() {
        List<PlayerEntity> players = new ArrayList<>();
        PlayerEntity player1 = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player1.setRating(77).setDefending(55).setPosition(PlayerPosition.DEFENDER);
        PlayerEntity player2 = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player2.setRating(77).setDefending(55).setPosition(PlayerPosition.WINGER);

        players.add(player1);
        players.add(player2);

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team1")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team.setPlayers(players).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        PlayerEntity player = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player.setRating(77).setDefending(55).setPosition(PlayerPosition.DEFENDER);

        when(mockTeamRepository.findByUser_Email("username")).thenReturn(team);

        toTest.updatePlayersList("username", player);

        verify(mockTeamRepository).saveAndFlush(teamEntityArgumentCaptor.capture());

        TeamEntity capture = teamEntityArgumentCaptor.getValue();

        Assertions.assertEquals(player.getAge(), capture.getPlayers().get(0).getAge());
    }
}
