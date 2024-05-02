package t1.openschool.task04.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String login;
    private String password;
    private String name;
    private Set<String> roles = new HashSet<>();
}
