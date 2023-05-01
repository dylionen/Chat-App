package pl.training.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.training.chat.model.LoggedUser;
import pl.training.chat.services.LoggedUserService;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
public class UsersController {
    private final LoggedUserService loggedUserService;

    public UsersController(LoggedUserService loggedUserService) {
        this.loggedUserService = loggedUserService;
    }

    @GetMapping("/registration/{username}")
    public ResponseEntity<Void> register(@PathVariable String username) {
        log.info("User create: " + username);
        try {
            loggedUserService.createUser(username);
        } catch (Exception e) {
            log.error(username + " exists");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {
        return loggedUserService.getAllUsers().stream().map(LoggedUser::getUsername).collect(Collectors.toSet());
    }

}
