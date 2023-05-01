package pl.training.chat.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.training.chat.login.User;
import pl.training.chat.login.UserDto;
import pl.training.chat.login.UserService;
import pl.training.chat.services.LoggedUserService;

@Controller
public class LoginController {

    private final UserService userService;
    private final LoggedUserService loggedUserService;

    public LoginController(UserService userService, LoggedUserService loggedUserService) {
        this.userService = userService;
        this.loggedUserService = loggedUserService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByName(userDto.getName());

        if (existingUser != null && existingUser.getName() != null && !existingUser.getName().isEmpty()) {
            result.rejectValue("name", null,
                    "Istnieje już konto o takiej nazwie");
        }

        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            result.rejectValue("repeatPassword", null,
                    "Hasła się różnią");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @PostMapping("/stay-login")
    public ResponseEntity<String> stayLogin(@RequestParam(name = "name") String name) {
        loggedUserService.stayLogin(name);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/update-avatar")
    public ResponseEntity<String> updateAvatar(@RequestParam(name = "avatar") String avatar) {
        userService.updateAvatar(avatar, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok("ok");
    }

}
