package softuni.futsalleague.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;
import softuni.futsalleague.service.CoachService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoachControllerIT {



    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeamRepository mockTeamRepository;
    @Autowired
    private CoachService mockCoachService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateCoach() throws Exception {

        UserEntity user = new UserEntity().setEmail("asd@asd.bg").setTeamName("asd")
                .setUsername("username").setFirstName("asd").setLastName("asd")
                .setPassword("asdasd");
        userRepository.save(user);


        TeamEntity team = new TeamEntity();
        team.setName("team");
        team.setUser(user);
        mockTeamRepository.save(team);
        mockMvc.perform(post("/coaches/add").
                        param("firstName", "Pesho").
                        param("lastName", "Peshov").
                        param("age", "33").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/"));


    }
}
