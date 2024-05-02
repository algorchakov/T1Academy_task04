package t1.openschool.task04.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.openschool.task04.model.RoleType;
import t1.openschool.task04.model.User;
import t1.openschool.task04.model.dto.UserRequestDTO;
import t1.openschool.task04.service.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO request) {
        userService.createUser(User.builder()
                .login(request.getLogin())
                .name(request.getName())
                .password(request.getPassword())
                .roles(request.getRoles().stream().map(role -> RoleType.valueOf(role.toUpperCase()))
                        .collect(Collectors.toSet()))
                .build());
        return ResponseEntity.ok("User created");
    }
}
