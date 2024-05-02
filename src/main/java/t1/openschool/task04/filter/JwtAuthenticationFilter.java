/*
package t1.openschool.task04.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import t1.openschool.task04.service.TokenService;
import t1.openschool.task04.service.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Получаем токен из заголовка
        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Обрезаем префикс и получаем имя пользователя из токена
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var login = tokenService.extractLogin(jwt);

        if (StringUtils.isNotEmpty(login) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(login);

            // Если токен валиден, то аутентифицируем пользователя
            if (tokenService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
*/
