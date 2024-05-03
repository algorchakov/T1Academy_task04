package t1.openschool.task04.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.task04.config.SecurityConfig;
import t1.openschool.task04.model.RoleType;
import t1.openschool.task04.model.User;
import t1.openschool.task04.model.dto.UserRequestDTO;
import t1.openschool.task04.service.TokenService;
import t1.openschool.task04.service.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicUserController.class)
@Import(SecurityConfig.class)
class PublicUserControllerTest {

    @MockBean
    UserService userService;
    @MockBean
    TokenService tokenService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        UserRequestDTO userDTO = UserRequestDTO.builder()
                .name("Test")
                .login("Test")
                .password("password")
                .roles(roles)
                .build();
        User user = User.builder()
                .login(userDTO.getLogin())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .roles(userDTO.getRoles().stream().map(role -> RoleType.valueOf(role.toUpperCase()))
                        .collect(Collectors.toSet()))
                .build();
        String userJSON = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(post("/api/public/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).createUser(user);
    }
}