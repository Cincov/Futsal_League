package softuni.futsalleague.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import softuni.futsalleague.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;


}
