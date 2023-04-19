package softuni.futsalleague.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;
import softuni.futsalleague.repository.UserRoleRepository;

@ExtendWith(MockitoExtension.class)
class InitServiceTest {

    @Mock
    private UserRoleRepository mockUserRoleRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private String mockDefaultPassword;

    private InitService toTest;

    @BeforeEach
    void setUp() {
        toTest = new InitService(mockUserRoleRepository, mockUserRepository
        , mockTeamRepository, mockPasswordEncoder, mockDefaultPassword);

    }



}
