package pl.training.chat.services;

import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import pl.training.chat.model.LoggedUser;
import pl.training.chat.repository.LoggedUserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoggedUserService {

    private final LoggedUserRepository loggedUserRepository;

    public LoggedUserService(LoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }

    public Boolean userExists(String username) {
        return loggedUserRepository.findUserByUsername(username).isPresent();
    }

    @Transactional
    public void createUser(String user) throws Exception {
        if (loggedUserRepository.findUserByUsername(user).isPresent()) {
            // throw new Exception("User exists");
        } else {
            loggedUserRepository.save(new LoggedUser().setUsername(user).setRefreshDate(new Date()));
        }
    }

    public List<LoggedUser> getAllUsers() {
        List<LoggedUser> allUsers = loggedUserRepository.findAll();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, -40);
        List<LoggedUser> deleteList = allUsers
                .stream()
                .filter(loggedUser -> loggedUser.getRefreshDate().before(calendar.getTime()))
                .toList();

        loggedUserRepository.deleteAll(deleteList);

        allUsers.removeIf(loggedUser -> loggedUser.getRefreshDate().before(calendar.getTime()));

        return allUsers;
    }

    public void stayLogin(String userName) {
        Optional<LoggedUser> user = loggedUserRepository.findUserByUsername(userName);
        if (user.isPresent()) {
            LoggedUser loggedUser = user.get();
            loggedUser.setRefreshDate(new Date());
            loggedUserRepository.save(loggedUser);
        }
    }
}
