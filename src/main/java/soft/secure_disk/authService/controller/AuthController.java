package soft.secure_disk.authService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soft.secure_disk.authService.dto.RegisterRequest;
import soft.secure_disk.authService.exception.PasswordsNotMatchException;
import soft.secure_disk.authService.service.AuthService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/register")
    public String registerGet(@ModelAttribute("registerRequest") RegisterRequest registerRequest) {
        return "/users/form";
    }

    @PostMapping("/register")
    private String registerPost(@Valid RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            return registerGet(registerRequest);
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRetypePassword())) {
            throw new PasswordsNotMatchException("Passwords do not match");
        }

        authService.register(registerRequest);
        return "redirect:/auth/login";
    }

    @GetMapping("/token/{activationToken}")
    public ResponseEntity<String> activationEmail(@PathVariable("activationToken")
                                                          String activationToken) {
        authService.verifyActivationLink(activationToken);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
