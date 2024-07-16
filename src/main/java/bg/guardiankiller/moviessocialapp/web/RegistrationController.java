package bg.guardiankiller.moviessocialapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {
  @GetMapping("/register")
  public String getRegister() {
    return "/register";
  }

}
