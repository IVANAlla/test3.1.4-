package web6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String mainP() {

        return "index";
    }
}
