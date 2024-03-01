package com.lauro.sifyflixapi.restcontroller.login;

import com.lauro.sifyflixapi.dto.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * class responsible for user login and authentication in the Spring context
 */
@RestController
@RequestMapping("/login")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class LoginRestController {

    private final AuthenticationManager authenticationManager;

    public LoginRestController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserDto dto) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        this.authenticationManager.authenticate(authenticationToken);
        return ResponseEntity.ok(dto);
    }
}
