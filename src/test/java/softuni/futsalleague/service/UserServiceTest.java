package softuni.futsalleague.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.futsalleague.domein.dtos.binding.UserEditFormDTO;
import softuni.futsalleague.domein.dtos.binding.UserRegisterFormDto;
import softuni.futsalleague.domein.dtos.binding.UserWithRoleDTO;
import softuni.futsalleague.domein.dtos.view.UserViewModel;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.entities.UserRoleEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;
import softuni.futsalleague.repository.PlayerRepository;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;
import softuni.futsalleague.repository.UserRoleRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    private final String TEAM_NAME = "EspinozaDogs";
    private final String EMAIL = "admin@exam.com";
    private final String FIRST_NAME = "Admin";
    private final String LAST_NAME = "Adminov";
    private final String USERNAME = "admin";
    private final String PASSWORD = "asdasd";
    private final String ENCODED_PASSWORD = "encoded_password";

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private TeamRepository mockTeamRepository;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private PlayerRepository mockPlayerRepository;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    private UserService toTest;


    @BeforeEach
    void setUp() {
        toTest = new UserService(mockUserRepository, mockPasswordEncoder,
                mockTeamRepository, mockUserRoleRepository,
                mockModelMapper, mockPlayerRepository);

    }

    @Test
    void testUserRegistration() {

        // ARRANGE
        UserRegisterFormDto testRegistrationDTO = new UserRegisterFormDto().
                setFirstName(FIRST_NAME).
                setLastName(LAST_NAME).
                setEmail(EMAIL).
                setUsername(USERNAME).
                setTeamName(TEAM_NAME).
                setPassword(PASSWORD).
                setConfirmPassword(PASSWORD);

        toTest.registerUser(testRegistrationDTO);
        TeamEntity team = new TeamEntity();

        team.setUser(mockModelMapper.map(testRegistrationDTO, UserEntity.class));
        team.setName("Team").setBudget(BigDecimal.valueOf(10000));

        verify(mockUserRepository).save(any());
        verify(mockTeamRepository).save(any());
    }

    @Test
    void testFindUserById() {

        String newUsername = "admin2";
        UserEntity testUser = new UserEntity().builder()
                .email(EMAIL).build();

        lenient().when(mockUserRepository.findById(1L)).thenReturn(Optional.of(testUser));

    }

    @Test
    void testGetAllUsers() {

        UserEntity user = new UserEntity();
        user.setEmail(EMAIL);
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(user);

        lenient().when(mockUserRepository.findAllWithoutAdminov()).thenReturn(userEntities);

        List<UserWithRoleDTO> userWithRoleDTOS = toTest.getAllUsers();

        Assertions.assertEquals(1, userWithRoleDTOS.size());

    }

    @Test
    void testDeleteUser() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(EMAIL);

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team");

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mockTeamRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(team));

        mockPlayerRepository.deleteAllByTeamEntity_Id(1L);
        mockTeamRepository.deleteById(1L);

        toTest.deleteUser(1L);

    }

    @Test
    void testFindById() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);

        UserViewModel userViewModel = new UserViewModel().
                setId(1L).
                setUsername(USERNAME).
                setBudget(BigDecimal.valueOf(10000)).
                setRole(UserRoleEnums.MODERATOR);

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));

        UserViewModel userById = toTest.findUserById(1L);

    }

    @Test
    void testEditUser() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail(EMAIL);
        user.setUsername(USERNAME);

        TeamEntity team = new TeamEntity();
        team.setId(1L);
        team.setName("Team");

        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(new UserRoleEntity().setRole(UserRoleEnums.MODERATOR).setRole(UserRoleEnums.ADMIN).setRole(UserRoleEnums.USER));

        UserEditFormDTO userEditFormDTO = new UserEditFormDTO();
        userEditFormDTO.setId(1L).setUsername(USERNAME)
                .setAdmin("ADMIN").setUser("USER").setModerator("MODERATOR");

        lenient().when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        lenient().when(mockTeamRepository.findById(1L)).thenReturn(Optional.of(team));

        toTest.editUser(user.getId(), userEditFormDTO);


        verify(mockTeamRepository).saveAndFlush(any());
        verify(mockUserRepository).saveAndFlush(any());

    }
}
