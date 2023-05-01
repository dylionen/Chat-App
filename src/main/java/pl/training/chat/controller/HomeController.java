package pl.training.chat.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.training.chat.login.UserServiceImpl;
import pl.training.chat.login.UserView;

@Controller
public class HomeController {
    public final UserServiceImpl userService;

    public HomeController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        UserView userView = userService.findUserViewByName(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("userView", userView);

        return "index";
    }
}
