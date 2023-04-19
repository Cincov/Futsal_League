package softuni.futsalleague.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.futsalleague.service.PlayerService;
import softuni.futsalleague.service.TeamService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerIT {

    @Mock
    private  PlayerService playerService;
    @Mock
    private  TeamService teamService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testConfirmCreatePLayer() throws Exception {

        mockMvc.perform(post("/players/add")
                .param("firstName", "asdas")
                .param("lastName", "asdasd")
                .param("age", "22")
                .param("position", "wings")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
