package kafka.system.Front_for_bank_microservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String page(){
        return "index";
    }

    @GetMapping("/main")
    public String mains() {
        return "main";
    }

    @GetMapping("/payments")
    public String payments() {
        return "payments";
    }

    @GetMapping("/history")
    public String history() {
        return "history";
    }

    @GetMapping("/register/user")
    public String registerUser() {
        return "register";
    }
}
