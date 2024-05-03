package t1.openschool.task04.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "user_table")
public class User {
    @Id
    private String id;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private Set<RoleType> roles;
}
