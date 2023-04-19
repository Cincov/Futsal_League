package softuni.futsalleague.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.futsalleague.domein.dtos.binding.CoachCreateFormDto;
import softuni.futsalleague.domein.entities.CoachEntity;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.repository.CoachRepository;
import softuni.futsalleague.repository.TeamRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoachServiceTest {

    private final String FIRST_NAME = "Pesho";
    private final String LAST_NAME = "Peshov";
    private final int AGE = 33;

    @Spy
    private CoachRepository mockCoachRepository;
    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private TeamService mockTeamService;

    @Captor
    private ArgumentCaptor<CoachEntity> coachEntityArgumentCaptor;

    private CoachService toTest;

    @BeforeEach
    void setUp() {
        toTest = new CoachService(mockCoachRepository,
                mockTeamRepository, mockTeamService);

    }

    @Test
    void testCreateCoach() {

        CoachCreateFormDto coachCreateFormDto = new CoachCreateFormDto();
        coachCreateFormDto.setAge(35).setFirstName("coach").setLastName("asdasd");


        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team")
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(new UserEntity().setFirstName("Pepi").setUsername("pepi"));
        team.setPlayers(List.of());

      when(mockTeamRepository.findByUser_Email("username")).thenReturn(team);


       toTest.createCoach(coachCreateFormDto, "username");

        verify(mockCoachRepository).save(coachEntityArgumentCaptor.capture());

    }

}
