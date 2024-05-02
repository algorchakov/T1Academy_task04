package t1.openschool.task04.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.openschool.task04.model.dto.PasswordTokenRequest;
import t1.openschool.task04.model.dto.RefreshTokenRequest;
import t1.openschool.task04.model.dto.TokenResponse;
import t1.openschool.task04.service.SecurityService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody PasswordTokenRequest tokenRequest) {
        final TokenResponse token = securityService.login(tokenRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getAccessToken(RefreshTokenRequest tokenRequest) {
        final TokenResponse token = securityService.getAccessToken(tokenRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest tokenRequest) {
        final TokenResponse token = securityService.refreshToken(tokenRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
