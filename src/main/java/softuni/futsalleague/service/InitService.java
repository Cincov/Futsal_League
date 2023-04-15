package softuni.futsalleague.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.entities.UserRoleEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;
import softuni.futsalleague.repository.UserRoleRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InitService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    @Autowired
    public InitService(UserRoleRepository userRoleRepository,
                       UserRepository userRepository,
                       TeamRepository teamRepository, PasswordEncoder passwordEncoder,
                       @Value("asdasd") String defaultPassword) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @PostConstruct
    public void init() {
        initRoles();
        initUsers();
        initTeams();
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            Arrays.stream(UserRoleEnums.values()).forEach(userRoleEnums -> {
                UserRoleEntity role = new UserRoleEntity();
                role.setRole(userRoleEnums);
                userRoleRepository.save(role);
            });
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            initAdmin();
            initNormalUser();
        }
    }

    private void initTeams() {
        if (teamRepository.count() == 0) {
            initUsersTeams();
        }
    }


    private void initAdmin() {
        var adminUser = UserEntity.builder().
                email("admin@example.com").
                firstName("Admin").
                lastName("Adminov").
                teamName("Adminite").
                username("admin").
                password(passwordEncoder.encode(defaultPassword)).
                roles(userRoleRepository.findAll()).
                build();

        userRepository.save(adminUser);
    }

    private void initNormalUser() {

        List<UserRoleEntity> roles = new ArrayList<>();
        roles.add(userRoleRepository.findUserRoleEntityByRole(UserRoleEnums.USER).orElse(null));

        var normalUserOne = UserEntity.builder().
                email("pesho@pesho.bg").
                firstName("Pesho").
                lastName("Peshov").
                teamName("PeshoTeam").
                username("peshkata").
                roles(roles).
                password(passwordEncoder.encode(defaultPassword)).
                build();


        var normalUserTwo = UserEntity.builder().
                email("gosho@gosho.bg").
                firstName("Gosho").
                lastName("Goshov").
                teamName("GoshoTeam").
                username("goshkata").
                roles(roles).
                password(passwordEncoder.encode(defaultPassword)).
                build();

        userRepository.save(normalUserOne);
        userRepository.save(normalUserTwo);
    }

    private void initUsersTeams() {

        TeamEntity adminTeam = new TeamEntity();
        adminTeam.setUser(userRepository.findByEmail("admin@example.com").orElse(null));
                adminTeam.setName("Adminite").setBudget(BigDecimal.valueOf(10000));

        TeamEntity peshoTeam = new TeamEntity();
        peshoTeam.setUser(userRepository.findByEmail("pesho@pesho.bg").orElse(null));
        peshoTeam.setName("PeshoTeam").setBudget(BigDecimal.valueOf(10000));

        TeamEntity goshoTeam = new TeamEntity();
        goshoTeam.setUser(userRepository.findByEmail("gosho@gosho.bg").orElse(null));
        goshoTeam.setName("GoshoTeam").setBudget(BigDecimal.valueOf(10000));

        teamRepository.save(adminTeam);
        teamRepository.save(peshoTeam);
        teamRepository.save(goshoTeam);
    }
}
