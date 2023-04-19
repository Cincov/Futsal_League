package softuni.futsalleague.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.service.PlayerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MarketControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAllPlayersForSale() throws Exception {

        mockMvc.perform(get("/market/allPlayer"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("players"))
                .andExpect(view().name("market"));
    }

}
