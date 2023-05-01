package pl.training.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.training.chat.model.LoggedUser;

import java.util.Optional;

public interface LoggedUserRepository extends JpaRepository<LoggedUser, Long> {

    Optional<LoggedUser> findUserByUsername(String username);
}
