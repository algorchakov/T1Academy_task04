package t1.openschool.task04.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import t1.openschool.task04.exeption.AuthException;
import t1.openschool.task04.model.JwtAuthentication;
import t1.openschool.task04.model.User;
import t1.openschool.task04.model.dto.PasswordTokenRequest;
import t1.openschool.task04.model.dto.TokenResponse;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> refreshTokenMap = new HashMap<>();

    public TokenResponse login(PasswordTokenRequest tokenRequest) {
        final User user = userService.getByLogin(tokenRequest.getLogin());
        if (isPassMatch(tokenRequest.getPassword(), user)) {
            final String accessToken = tokenService.generateAccessToken(user);
            final String refreshToken = tokenService.generateRefreshToken(user);
            refreshTokenMap.put(user.getLogin(), refreshToken);
            return new TokenResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid password");
        }
    }

    public TokenResponse getAccessToken(String refreshToken) {
        final Claims claims = tokenService.getClaims(refreshToken);
        final String login = claims.getSubject();
        final String saveRefreshToken = refreshTokenMap.get(login);
        if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
            final User user = userService.getByLogin(login);
            final String accessToken = tokenService.generateAccessToken(user);
            return new TokenResponse(accessToken, null);
        }
        return new TokenResponse(null, null);
    }

    public TokenResponse refreshToken(String refreshToken) {
        final Claims claims = tokenService.getClaims(refreshToken);
        final String login = claims.getSubject();
        final String saveRefreshToken = refreshTokenMap.get(login);
        if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
            final User user = userService.getByLogin(login);
            final String accessToken = tokenService.generateAccessToken(user);
            final String newRefreshToken = tokenService.generateRefreshToken(user);
            refreshTokenMap.put(user.getLogin(), newRefreshToken);
            return new TokenResponse(accessToken, null);
        }
        throw new AuthException("Invalid token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isPassMatch(String password, User user) {
        return passwordEncoder.matches(password, userService
                .getByLogin(user.getLogin())
                .getPassword());
    }
}
