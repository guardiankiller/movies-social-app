package bg.guardiankiller.moviessocialapp.rest;

import bg.guardiankiller.moviessocialapp.model.dto.UserChangeDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRegisterRestDTO;
import bg.guardiankiller.moviessocialapp.model.dto.UserRolesDTO;
import bg.guardiankiller.moviessocialapp.model.dto.ViolationResponseDTO;
import bg.guardiankiller.moviessocialapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "users", description = "User APIs")
public class UserRestController {

    private final UserService userService;

    @Operation(
        summary = "register a new user",
        description = "Register's a new user given their information",
        responses = {
            @ApiResponse(responseCode = "201",
                description = "If the new user is successfully created", content = @Content),
            @ApiResponse(responseCode = "200",
                description = "If dryRun is true and the provided new user data is valid", content = @Content),
            @ApiResponse(responseCode = "400",
                description = "If the provided new user data is invalid",
                content = @Content(array =
                @ArraySchema(schema = @Schema(implementation = ViolationResponseDTO.class)))),
        })
    @PostMapping
    public ResponseEntity<?> registerUser(
            @Parameter(description = "The required information needed to create a new user") @RequestBody UserRegisterDTO form,
            @Parameter(description = "If true the submitted user registration data will only be validated")
            @RequestParam(required = false) boolean dryRun) {

        userService.registerUser(form, dryRun);

        if(dryRun) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @Operation(
        summary = "retrieve a singe user",
        description = "Retrieves a single user given their username",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "If the user with the given username is found",
                content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404",
                description = "If user with the given username does not exist",
                content = @Content)
        })
    @GetMapping("{username}")
    public ResponseEntity<UserDTO> findByUsername(
        @Parameter(description = "The user's username.", required = true) @PathVariable String username) {
        return ResponseEntity.of(userService.findUserByUsername(username));
    }

    @Operation(
        summary = "update the information of a singe user",
        description = "Updates the user information based on the provided information",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "If the user is successfully modified",
                content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "200",
                description = "If dryRun is true and the provided user data update is valid",
                content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400",
                description = "If the provided user data is invalid",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ViolationResponseDTO.class))))
        })
    @PutMapping("{username}")
    @PreAuthorize("#username == authentication.name")
    public ResponseEntity<UserDTO> changeUserInfo(
        @Parameter(description = "The user's username.", required = true) @PathVariable String username,
        @Parameter(description = "If true the submitted user registration data will only be validated")
        @RequestParam(required = false) boolean dryRun,
        @Parameter(description = "Holds only the fields that need to be updated on the user")
        @RequestBody UserChangeDTO change) {
        return ResponseEntity.of(userService.changeUserInfo(username, change, dryRun));
    }

    @Operation(
        summary = "add roles to user",
        description = "Adds additional roles to the user",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "If the update is successful",
                content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400",
                description = "If the provided roles are invalid",
                content = @Content)
        })
    @PostMapping("{username}/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> addUserRoles(
        @Parameter(description = "The user's username.", required = true) @PathVariable String username,
        @Parameter(description = "the array of roles", required = true) @RequestBody UserRolesDTO roles) {
        return ResponseEntity.of(userService.addUserRoles(username, roles.getRoles()));
    }

    @DeleteMapping("{username}/roles")
    @Operation(
        summary = "remove roles from the user",
        description = "Removes roles from the user",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "If the update is successful",
                content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400",
                description = "If the provided roles are invalid",
                content = @Content)
        })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> removeUserRoles(
        @Parameter(description = "The user's username.", required = true) @PathVariable String username,
        @Parameter(description = "the array of roles", required = true) @RequestBody UserRolesDTO roles) {
        return ResponseEntity.of(userService.removeUserRoles(username, roles.getRoles()));
    }

    @PutMapping("{username}/roles")
    @Operation(
        summary = "update the roles for the user",
        description = "Updates the user's roles to the provided",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "If the update is successful",
                content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400",
                description = "If the provided roles are invalid",
                content = @Content)
        })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> setUserRoles(
        @Parameter(description = "The user's username.", required = true) @PathVariable String username,
        @Parameter(description = "the array of roles", required = true) @RequestBody UserRolesDTO roles) {
        return ResponseEntity.of(userService.setUserRoles(username, roles.getRoles()));
    }

    @DeleteMapping("{username}")
    @Operation(
        summary = "delete the user",
        description = "Deletes the user given their username",
        responses = {
            @ApiResponse(responseCode = "204",
                description = "If the update is deleted",
                content = @Content),
            @ApiResponse(responseCode = "404",
                description = "If the user is not found",
                content = @Content)
        })
    @PreAuthorize("hasAuthority('ADMIN') || #username == authentication.name")
    public ResponseEntity<?> removeUser(
        @Parameter(description = "The user's username.", required = true) @PathVariable String username) {
        var code = userService.deleteUser(username).map(x->HttpStatus.NO_CONTENT).orElse(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(code).build();
    }
}
