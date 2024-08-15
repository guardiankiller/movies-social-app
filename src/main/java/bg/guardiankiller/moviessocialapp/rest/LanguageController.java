package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.Language;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @GetMapping
    public List<String> languages() {
        return Arrays.stream(Language.values()).map(Language::name).toList();
    }
}
