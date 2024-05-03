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
import t1.openschool.task04.model.dto.PasswordTokenRequest;
import t1.openschool.task04.model.dto.RefreshTokenRequest;
import t1.openschool.task04.service.SecurityService;
import t1.openschool.task04.service.TokenService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityController.class)
@Import(SecurityConfig.class)
class SecurityControllerTest {

    @MockBean
    SecurityService securityService;
    @MockBean
    TokenService tokenService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void login() throws Exception {
        PasswordTokenRequest tokenRequest = PasswordTokenRequest.builder()
                .login("test")
                .password("pass")
                .build();
        String tokenReqJSON = objectMapper.writeValueAsString(tokenRequest);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tokenReqJSON))
                .andExpect(status().isOk());
        verify(securityService, times(1)).login(tokenRequest);
    }

    @Test
    void getAccessToken() throws Exception {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().refreshToken("12345").build();
        String refreshTokenReqJSON = objectMapper.writeValueAsString(refreshTokenRequest);
        mockMvc.perform(post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshTokenReqJSON))
                .andExpect(status().isOk());
        verify(securityService, times(1)).getAccessToken(refreshTokenRequest.getRefreshToken());
    }

    @Test
    void refreshToken() throws Exception {
        RefreshTokenRequest refreshTokenRequest = RefreshTokenRequest.builder().refreshToken("12345").build();
        String refreshTokenReqJSON = objectMapper.writeValueAsString(refreshTokenRequest);
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshTokenReqJSON))
                .andExpect(status().isOk());
        verify(securityService, times(1)).refreshToken(refreshTokenRequest.getRefreshToken());
    }
}