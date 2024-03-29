package softuni.futsalleague.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("email", "pesho@example.com").
                        param("firstName", "Pesho").
                        param("lastName", "Peshov").
                        param("password", "asdasd").
                        param("confirmPassword", "asdasd").
                        param("teamName", "PeshosTeam").
                        param("username", "pepi").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/login"));

    }

    @Test
    void testRegistrationWithError() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("email", "pesho@example.com").
                        param("firstName", "1").
                        param("lastName", "Peshov").
                        param("password", "asdasd").
                        param("confirmPassword", "asdasd").
                        param("teamName", "PeshosTeam").
                        param("username", "pepi").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/register"));

    }
}
