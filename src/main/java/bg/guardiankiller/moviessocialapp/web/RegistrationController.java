package bg.guardiankiller.moviessocialapp.web;

import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import bg.guardiankiller.moviessocialapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        if (!model.containsAttribute("userRegisterDTO")) {
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        }
        return "/register";
    }

    @PostMapping("/register")
    public String postRegister(
            UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            for (var entry : model.asMap().entrySet()) {
                redirectAttributes
                        .addFlashAttribute(
                                entry.getKey(),
                                entry.getValue()
                        );
            }

            return "redirect:/register";
        }
        userService.registerUser(userRegisterDTO);

        return "redirect:/login";
    }

}
