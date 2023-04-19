package softuni.futsalleague.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.futsalleague.domein.dtos.view.TeamViewModel;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.entities.UserRoleEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;
import softuni.futsalleague.repository.UserRoleRepository;
import softuni.futsalleague.service.TeamService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testMyTeam() throws Exception {

        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnums.ADMIN);

        userRoleRepository.save(role);

        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(role);

        UserEntity user = new UserEntity();
        user.setUsername("username").setEmail("asd@asd.bg")
                .setId(1L);
        user.setFirstName("Pepi").setLastName("peshov")
                .setPassword("asdasd").setTeamName("Team")
                .setRoles(roles);

        userRepository.save(user);

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setUser(user);
        team.setName(user.getTeamName())
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(user);
        team.setPlayers(List.of()).setUser(user);

        teamRepository.save(team);

        TeamViewModel teamViewModel = new TeamViewModel();
        teamViewModel.setId(team.getId()).setRating(team.getRating())
                .setName(team.getName()).setCoachName("asdasd")
                .setBudget(team.getBudget());
        lenient().when(teamRepository.findByUser_Email("username")).thenReturn(team);
        lenient().when(teamService.viewMyTeam("username")).thenReturn(teamViewModel);


        mockMvc.perform(post("/teams/myTeam")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("team"))
                .andExpect(model().attributeExists("goalkeepers"))
                .andExpect(model().attributeExists("defenders"))
                .andExpect(model().attributeExists("wings"))
                .andExpect(model().attributeExists("forwards"))
                .andExpect(view().name("my-team"));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testTable() throws Exception {
        UserRoleEntity role = new UserRoleEntity();
        role.setRole(UserRoleEnums.ADMIN);

        userRoleRepository.save(role);

        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(role);

        UserEntity user = new UserEntity();
        user.setUsername("username").setEmail("asd@asd.bg")
                .setId(1L);
        user.setFirstName("Pepi").setLastName("peshov")
                .setPassword("asdasd").setTeamName("Team")
                .setRoles(roles);

        userRepository.save(user);

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setUser(user);
        team.setName(user.getTeamName())
                .setBudget(BigDecimal.valueOf(50000))
                .setRating(77).setUser(user);
        team.setPlayers(List.of()).setUser(user);

        teamRepository.save(team);

        mockMvc.perform(get("/teams/myTeam"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("teams"))
                .andExpect(view().name("table"));

    }
}
