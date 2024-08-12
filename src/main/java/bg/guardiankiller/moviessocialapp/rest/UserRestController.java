package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterRestDTO;
import bg.guardiankiller.moviessocialapp.service.UserRestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRestService userRestService;

    public UserRestController(UserRestService userRestService) {
        this.userRestService = userRestService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegisterRestDTO form,
            @RequestParam(required = false) boolean dryRun) {

        userRestService.registerUser(form, dryRun);

        if(dryRun) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.of(userRestService.findUserByUsername(username));
    }
}
