package softuni.futsalleague.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import softuni.futsalleague.domein.dtos.binding.PlayerCreateFormDto;
import softuni.futsalleague.domein.dtos.view.PlayerViewModel;
import softuni.futsalleague.domein.entities.CoachEntity;
import softuni.futsalleague.domein.entities.PlayerEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.enums.PlayerPosition;
import softuni.futsalleague.repository.PlayerRepository;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    private final String FIRST_NAME = "Pesho";
    private final String LAST_NAME = "Peshov";
    private final int AGE = 23;
    private final PlayerPosition POSITION = PlayerPosition.GOALKEEPER;
    private final int PACE = 55;
    private final int SHOOTING = 66;
    private final int PASSING = 66;
    private final int DRIBBLING = 77;
    private final int DEFENDING = 88;
    private final int RATING = 77;
    private final Long COMMON_ID = 1L;


    @Mock
    private PlayerRepository mockPlayerRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private TeamService mockTeamService;
    @Mock
    private ModelMapper mockModelMapper;

    @Captor
    private ArgumentCaptor<PlayerEntity> playerEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<TeamEntity> teamEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @InjectMocks
    private PlayerService toTest;

    @BeforeEach
    void setUp() {
        toTest = new PlayerService(mockPlayerRepository, mockUserRepository,
                mockTeamRepository, mockTeamService, mockModelMapper);
    }

    @Test
    void testCreatePlayer() {

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team1")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        PlayerCreateFormDto playerCreateFormDto = new PlayerCreateFormDto();
        playerCreateFormDto.setAge(22).setFirstName("asd").setLastName("asd").setPosition(String.valueOf(POSITION));

        lenient().when(mockTeamRepository.findByUser_Email("username")).thenReturn(team);

        toTest.createPlayer(playerCreateFormDto, "username");

        verify(mockPlayerRepository).save(playerEntityArgumentCaptor.capture());

        PlayerEntity player = playerEntityArgumentCaptor.getValue();

        Assertions.assertEquals(playerCreateFormDto.getAge(), player.getAge());

    }

    @Test
    void testSendToMarket() {

        PlayerEntity player = new PlayerEntity();
        player.setAge(22).setFirstName("asd").setLastName("asd");

        when(mockPlayerRepository.findById(1L)).thenReturn(Optional.of(player));
        toTest.sendToMarket(1L);

        verify(mockPlayerRepository).save(playerEntityArgumentCaptor.capture());

        PlayerEntity playerEntityArgumentCaptorValue = playerEntityArgumentCaptor.getValue();

        Assertions.assertEquals(true, playerEntityArgumentCaptorValue.getSale());

    }

    @Test
    void testFindAllPlayerForSale() {
        List<PlayerEntity> players = new ArrayList<>();
        PlayerEntity player1 = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player1.setRating(77).setDefending(55).setPosition(PlayerPosition.DEFENDER);
        PlayerEntity player2 = (PlayerEntity) new PlayerEntity().setAge(22).setFirstName("1").setLastName("2");
        player2.setRating(77).setDefending(55).setPosition(PlayerPosition.WINGER);

        players.add(player1);
        players.add(player2);

        when(mockPlayerRepository.findPlayerEntitiesBySale("username")).thenReturn(players);

        List<PlayerViewModel> username = toTest.findAllPlayerForSale("username");

    }

    @Test
    void testBuyPlayer() {
        List<PlayerEntity> players = new ArrayList<>();
        List<PlayerEntity> players2 = new ArrayList<>();

        PlayerEntity player = new PlayerEntity();
        player.setAge(22).setFirstName("asd").setLastName("asd");
        player.setRating(55).setPosition(POSITION);
        PlayerEntity player2 = new PlayerEntity();
        player.setAge(22).setFirstName("asd").setLastName("asd");
        player.setRating(55).setPosition(POSITION);

        players.add(player);
        players2.add(player2);

        TeamEntity playerTeam = new TeamEntity();
        playerTeam.setId(1L);
        playerTeam.setName("playerTeam")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("asd").setUsername("asd"));
        playerTeam.setPlayers(players).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        TeamEntity buyerTeam = new TeamEntity();
        buyerTeam.setId(2L);
        buyerTeam.setName("Team1")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        buyerTeam.setPlayers(players2).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        player.setTeamEntity(playerTeam);

        when(mockPlayerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(mockTeamRepository.findTeamEntityByName(player.getTeamEntity().getName())).thenReturn(Optional.of(playerTeam));
        when(mockTeamRepository.findByUser_Email("username")).thenReturn(buyerTeam);


        toTest.buyPlayer(1L, "username");

//        verify(mockTeamRepository).saveAndFlush(teamEntityArgumentCaptor.capture());
//        verify(mockTeamRepository).saveAndFlush(teamEntityArgumentCaptor.capture());
//        verify(mockPlayerRepository).saveAndFlush(playerEntityArgumentCaptor.capture());

    }

    @Test
    void testQuickSale() {
        List<PlayerEntity> players = new ArrayList<>();

        PlayerEntity player = new PlayerEntity();
        player.setAge(22).setFirstName("asd").setLastName("asd");
        player.setRating(55).setPosition(POSITION).setPace(66).setDefending(66)
                .setShooting(66).setDribbling(66).setPassing(66);

        players.add(player);

        TeamEntity playerTeam = new TeamEntity();
        playerTeam.setId(1L);
        playerTeam.setName("playerTeam")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("asd").setUsername("asd"));
        playerTeam.setPlayers(players).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        player.setTeamEntity(playerTeam);

        when(mockTeamRepository.findByUser_Email("username")).thenReturn(playerTeam);
        when(mockPlayerRepository.findById(1L)).thenReturn(Optional.of(player));

        toTest.quickSale(1L, "username");

    }

    @Test
    void testFindById() {
        PlayerEntity player = new PlayerEntity();
        player.setAge(22).setFirstName("asd").setLastName("asd");
        player.setRating(55).setPosition(POSITION).setPace(66).setDefending(66)
                .setShooting(66).setDribbling(66).setPassing(66);

        TeamEntity playerTeam = new TeamEntity();
        playerTeam.setId(1L);
        playerTeam.setName("playerTeam")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("asd").setUsername("asd"));
        playerTeam.setPlayers(List.of()).setCoachEntity((CoachEntity) new CoachEntity().setLastName("last_name"));

        player.setTeamEntity(playerTeam);

        when(mockPlayerRepository.findById(1L)).thenReturn(Optional.of(player));

        toTest.findById(1L);



    }
}
