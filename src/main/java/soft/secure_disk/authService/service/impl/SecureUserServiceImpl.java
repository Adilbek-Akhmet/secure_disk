package soft.secure_disk.authService.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soft.secure_disk.authService.dto.RegisterRequest;
import soft.secure_disk.authService.exception.UserWithSuchEmailFoundException;
import soft.secure_disk.authService.service.ActivationLinkService;
import soft.secure_disk.authService.service.SecureUserService;
import soft.secure_disk.userService.model.Role;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.time.LocalDate;

@Log4j2
@Service
@RequiredArgsConstructor
public class SecureUserServiceImpl implements SecureUserService {

    private final UserService userService;
    private final ActivationLinkService activationLinkService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String saveNotActivatedEmail(RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            User currentUser = userService.findByEmail(registerRequest.getEmail());

            if (currentUser.isEnabled()) {
                throw new UserWithSuchEmailFoundException("Username " + registerRequest.getEmail() + " exists. Please write another email");
            } else {
                activationLinkService.deleteByUser(currentUser);
                userService.deleteByEmail(registerRequest.getEmail());
            }
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDate.now())
                .build();

        userService.save(user);
        log.info("No active user {} is saved", user);

        return registerRequest.getEmail();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email);
    }
}
