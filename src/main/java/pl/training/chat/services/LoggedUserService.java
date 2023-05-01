package pl.training.chat.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.training.chat.login.User;
import pl.training.chat.login.UserRepository;
import pl.training.chat.model.LoggedUser;
import pl.training.chat.repository.LoggedUserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoggedUserService {

    private final LoggedUserRepository loggedUserRepository;
    private final UserRepository userRepository;

    public LoggedUserService(LoggedUserRepository loggedUserRepository, UserRepository userRepository) {
        this.loggedUserRepository = loggedUserRepository;
        this.userRepository = userRepository;
    }

    public Boolean userExists(String userName) {
        return loggedUserRepository.findLoggedUserByUser(userRepository.findByName(userName

        )).isPresent();
    }

    @Transactional
    public void createUser(String userName) throws Exception {
        if (loggedUserRepository.findLoggedUserByUser(userRepository.findByName(userName)).isEmpty()) {
            User dbUser = userRepository.findByName(userName);
            loggedUserRepository.save(new LoggedUser().setUser(dbUser).setRefreshDate(new Date()));
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
        Optional<LoggedUser> user = loggedUserRepository.findLoggedUserByUser(userRepository.findByName(userName));
        if (user.isPresent()) {
            LoggedUser loggedUser = user.get();
            loggedUser.setRefreshDate(new Date());
            loggedUserRepository.save(loggedUser);
        }
    }

}
