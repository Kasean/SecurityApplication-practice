package org.kasean.api;

import org.kasean.services.SecretService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger("ViewController");

    private final SecretService secretService;

    public ViewController(SecretService secretService) {
        this.secretService = secretService;
    }

    @PostMapping("/createSecret")
    public String getInfo(Model model, @RequestParam(value = "secret") String secret, @AuthenticationPrincipal User principal) {
        model.addAttribute("link", secretService.createSecret(secret));
        return "createSecret";
    }
    @GetMapping("/createSecret")
    public String getInfo(Model model, @AuthenticationPrincipal User principal) {
        return "createSecret";
    }

    @GetMapping("/showSecret/{id}")
    public String getAdmin(Model model, @PathVariable String id, @AuthenticationPrincipal User principal) {
        LOGGER.info("Request: {} user {}", id, principal.getUsername());

        var data = secretService.getData(id);
        model.addAttribute("secret", data);
        return "showSecret";
    }
}
