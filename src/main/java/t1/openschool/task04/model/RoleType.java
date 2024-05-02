package t1.openschool.task04.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleType implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    private final String vale;
    @Override
    public String getAuthority() {
        return vale;
    }
}
