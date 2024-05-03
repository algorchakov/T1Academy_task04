package t1.openschool.task04.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import t1.openschool.task04.model.RoleType;
import t1.openschool.task04.model.User;
import t1.openschool.task04.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    void createUserShouldCreateAndSaveUser() {
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.USER);
        User user = User.builder()
                .login("Test")
                .name("Test")
                .password("password")
                .roles(roles)
                .build();
        userService.createUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void getByLoginShouldReturnUserByLogin() {
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.USER);
        User user = User.builder()
                .login("Test")
                .name("Test")
                .password("password")
                .roles(roles)
                .build();
        Mockito.when(userRepository.findByLogin("Test")).thenReturn(Optional.ofNullable(user));
        User user1 = userService.getByLogin("Test");
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user1, user);
    }
}