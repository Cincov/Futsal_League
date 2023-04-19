package softuni.futsalleague.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.futsalleague.domein.dtos.binding.UserEditFormDTO;
import softuni.futsalleague.domein.dtos.binding.UserRegisterFormDto;
import softuni.futsalleague.domein.dtos.binding.UserWithRoleDTO;
import softuni.futsalleague.domein.dtos.view.UserViewModel;
import softuni.futsalleague.domein.entities.TeamEntity;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.entities.UserRoleEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;
import softuni.futsalleague.exeption.ObjectNotFoundException;
import softuni.futsalleague.repository.PlayerRepository;
import softuni.futsalleague.repository.TeamRepository;
import softuni.futsalleague.repository.UserRepository;
import softuni.futsalleague.repository.UserRoleRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PlayerRepository playerRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, TeamRepository teamRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper,
                       PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamRepository = teamRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.playerRepository = playerRepository;
    }

    public void registerUser(UserRegisterFormDto registrationDTO) {

        UserEntity userEntity = new UserEntity().
                setFirstName(registrationDTO.getFirstName()).
                setLastName(registrationDTO.getLastName()).
                setEmail(registrationDTO.getEmail()).
                setPassword(passwordEncoder.encode(registrationDTO.getPassword())).
                setTeamName(registrationDTO.getTeamName()).
                setUsername(registrationDTO.getUsername());

        userRepository.save(userEntity);

        TeamEntity team = new TeamEntity();
        team.setUser(userEntity);
        team.setName(userEntity.getTeamName());
        team.setBudget(BigDecimal.valueOf(10000));

        teamRepository.save(team);
    }

    public List<UserWithRoleDTO> getAllUsers() {
        List<UserWithRoleDTO> list = userRepository.findAllWithoutAdminov().
                stream().
                map(UserWithRoleDTO::new).
                toList();

        return list;
    }

    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId).
                orElseThrow(() -> new ObjectNotFoundException("User not found"));;
        TeamEntity team = teamRepository.findByUser_Id(user.getId()).
                orElseThrow(() -> new ObjectNotFoundException("Team not found"));;
        playerRepository.deleteAllByTeamEntity_Id(team.getId());
        teamRepository.deleteById(team.getId());
        userRepository.deleteById(userId);
    }

    public UserViewModel findUserById(Long userId) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        return modelMapper.map(byId, UserViewModel.class);
    }

    @Transactional
    public void editUser(Long userId, UserEditFormDTO userEditFormDTO) {

        UserEntity user = userRepository.findById(userId).
                orElseThrow(() -> new ObjectNotFoundException("User not found"));;
        TeamEntity team = teamRepository.findByUser_Id(userId).
                orElseThrow(() -> new ObjectNotFoundException("Team not found"));;
        List<UserRoleEntity> roles = new ArrayList<>();

        if (userEditFormDTO.getModerator() != null) {
            roles.add(userRoleRepository.findUserRoleEntityByRole(UserRoleEnums.MODERATOR).
                    orElseThrow(() -> new ObjectNotFoundException("Role not found")));
        }
        if (userEditFormDTO.getUser() != null) {
            roles.add(userRoleRepository.findUserRoleEntityByRole(UserRoleEnums.USER).
                    orElseThrow(() -> new ObjectNotFoundException("Role not found")));
        }
        if (userEditFormDTO.getAdmin() != null) {
            roles.add(userRoleRepository.findUserRoleEntityByRole(UserRoleEnums.ADMIN).
                    orElseThrow(() -> new ObjectNotFoundException("Role not found")));
        }
        if (userEditFormDTO.getUsername() != null) {
            user.setUsername(userEditFormDTO.getUsername());
        }
        if (!roles.isEmpty()) {
            user.setRoles(roles);
        }

        if (userEditFormDTO.getBudget() != null) {
            team.setBudget(userEditFormDTO.getBudget());
        }

        teamRepository.saveAndFlush(team);
        userRepository.saveAndFlush(user);

    }
}
