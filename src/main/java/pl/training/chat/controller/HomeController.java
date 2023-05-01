package pl.training.chat.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Model model) {

        model.addAttribute("userName", SecurityContextHolder.getContext().getAuthentication().getName());
        return "index";
    }
}
