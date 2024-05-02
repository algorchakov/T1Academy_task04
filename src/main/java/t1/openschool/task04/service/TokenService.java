package t1.openschool.task04.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import t1.openschool.task04.model.User;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String ROLE_CLAIM = "roles";
    private static final String ID_CLAIM = "id";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.tokenExpiration}")
    private Duration tokenExpiration;
    @Value("${jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    public String generateAccessToken(User user) {
        return Jwts.builder().setSubject(user.getLogin()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .claim(ROLE_CLAIM, user.getRoles())
                .claim(ID_CLAIM, user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder().setSubject(user.getLogin()).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + refreshTokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
