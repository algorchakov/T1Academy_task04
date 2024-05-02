package t1.openschool.task04.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import t1.openschool.task04.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByLogin(String login);
}
