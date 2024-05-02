package t1.openschool.task04.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import t1.openschool.task04.model.User;
import t1.openschool.task04.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByLogin;
    }

}
