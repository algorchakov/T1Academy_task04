package t1.openschool.task04.model.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserRequestDTO {
    private String login;
    private String password;
    private String name;
    private Set<String> roles = new HashSet<>();
}
