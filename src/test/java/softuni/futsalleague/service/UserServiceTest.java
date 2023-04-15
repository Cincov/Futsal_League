package softuni.futsalleague.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.futsalleague.domein.dtos.binding.UserRegisterFormDto;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private TeamRepository mockTeamRepository;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    private UserService toTest;

    @BeforeEach
    void setUp() {
        toTest = new UserService(
                mockUserRepository,
                mockPasswordEncoder,
                mockTeamRepository);
    }

    @Test
    void testUserRegistration_SaveInvoked() {

        //LEFT for reference

        // ARRANGE
        UserRegisterFormDto testRegistrationDTO = new UserRegisterFormDto().
                setEmail("test@example.com").
                setFirstName("Test").
                setLastName("Testov").
                setPassword("asdasd");

        toTest.registerUser(testRegistrationDTO);

        verify(mockUserRepository).save(any());
    }

    @Test
    void testUserRegistration_SaveInvoked_Version2() {

        String testPassword = "asdasd";
        String encodedPassword = "encoded_password";
        String email = "test@example.com";
        String firstName = "Test";
        String lastName = "Testov";

        UserRegisterFormDto testRegistrationDTO = new UserRegisterFormDto().
                setEmail("test@example.com").
                setFirstName(firstName).
                setLastName(lastName).
                setPassword(testPassword);

        when(mockPasswordEncoder.encode(testRegistrationDTO.getPassword())).
                thenReturn(encodedPassword);

        toTest.registerUser(testRegistrationDTO);

        verify(mockUserRepository).save(userEntityArgumentCaptor.capture());

        UserEntity actualSavedUser = userEntityArgumentCaptor.getValue();
        assertEquals(testRegistrationDTO.getEmail(), actualSavedUser.getEmail());
        assertEquals(encodedPassword, actualSavedUser.getPassword());


    }
}
