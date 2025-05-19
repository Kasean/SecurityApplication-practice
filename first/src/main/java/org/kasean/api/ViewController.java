package org.kasean.api;

import org.kasean.securities.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @GetMapping("/info")
    public String getInfo(Model model, @AuthenticationPrincipal User principal) {
        model.addAttribute("message", "Hello, " + principal.getUsername());
        return "info";
    }
    @GetMapping("/admin")
    public String getAdmin(Model model, @AuthenticationPrincipal User principal) {
        model.addAttribute("message", "Hello, " + principal.getUsername());
        return "admin";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("message", "It is about page.");
        return "about";
    }

    @GetMapping("/blockedUsers")
    public String getBlockedUsers(Model model) {
        List<String> blockedUsers = loginAttemptService.getBlockedUsers();
        model.addAttribute("users", blockedUsers);
        return "blockedUsers";
    }
}
