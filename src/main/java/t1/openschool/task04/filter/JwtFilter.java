package t1.openschool.task04.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import t1.openschool.task04.model.JwtAuthentication;
import t1.openschool.task04.model.RoleType;
import t1.openschool.task04.service.TokenService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null) {
            final Claims claims = tokenService.getClaims(token);
            final JwtAuthentication jwtInfoToken = generate(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUsername(claims.get("username", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<RoleType> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toSet());
    }
}