package bg.guardiankiller.moviessocialapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  @GetMapping("/login")
  public String getLogin() {
    return "/login";
  }

}
