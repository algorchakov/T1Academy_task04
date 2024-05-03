package t1.openschool.task04.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import t1.openschool.task04.config.SecurityConfig;
import t1.openschool.task04.filter.JwtFilter;
import t1.openschool.task04.service.SecurityService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {
    @MockBean
    SecurityService securityService;
    @MockBean
    JwtFilter jwtFilter;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void user() throws Exception {
        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}