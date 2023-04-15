package softuni.futsalleague.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.futsalleague.domein.entities.UserEntity;
import softuni.futsalleague.domein.enums.UserRoleEnums;
import softuni.futsalleague.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {

  private final String NOT_EXISTING_EMAIL = "pesho@example.com";

  private ApplicationUserDetailsService toTest;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void setUp() {
    toTest = new ApplicationUserDetailsService(
        mockUserRepository
    );
  }

  @Test
  void testUserFound() {

    // ARRANGE
    softuni.futsalleague.domein.entities.UserRoleEntity testAdminRole = new softuni.futsalleague.domein.entities.UserRoleEntity().setRole(UserRoleEnums.ADMIN);
    softuni.futsalleague.domein.entities.UserRoleEntity testUserRole = new softuni.futsalleague.domein.entities.UserRoleEntity().setRole(UserRoleEnums.USER);

    String EXISTING_EMAIL = "admin@example.com";
    UserEntity testUserEntity = new UserEntity().
        setEmail(EXISTING_EMAIL).
        setPassword("asdasd").
        setRoles(List.of(testAdminRole, testUserRole));


    when(mockUserRepository.findByEmail(EXISTING_EMAIL)).
        thenReturn(Optional.of(testUserEntity));

    UserDetails adminDetails =
        toTest.loadUserByUsername(EXISTING_EMAIL);

    Assertions.assertNotNull(adminDetails);
    Assertions.assertEquals(EXISTING_EMAIL, adminDetails.getUsername());
    Assertions.assertEquals(testUserEntity.getPassword(), adminDetails.getPassword());

    Assertions.assertEquals(2,
        adminDetails.getAuthorities().size(),
        "The authorities are supposed to be just two - ADMIN/USER.");

    assertRole(adminDetails.getAuthorities(), "ROLE_ADMIN");
    assertRole(adminDetails.getAuthorities(), "ROLE_USER");
    
  }

  private void assertRole(Collection<? extends GrantedAuthority> authorities,
    String role) {
    authorities.
        stream().
        filter(a -> role.equals(a.getAuthority())).
        findAny().
        orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
  }


  @Test
  void testUserNotFound() {
    assertThrows(
        UsernameNotFoundException.class,
        () -> toTest.loadUserByUsername(NOT_EXISTING_EMAIL)
    );
  }
}
