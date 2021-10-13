package soft.secure_disk.authService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soft.secure_disk.authService.dto.LoginRequest;
import soft.secure_disk.authService.dto.RegisterRequest;
import soft.secure_disk.authService.model.ActivationLink;
import soft.secure_disk.authService.service.ActivationLinkService;
import soft.secure_disk.authService.service.AuthService;
import soft.secure_disk.authService.service.SecureUserService;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final SecureUserService secureUserService;
    private final ActivationLinkService activationLinkService;

    @Override
    public void register(RegisterRequest registerRequest) {
        String email = secureUserService.saveNotActivatedEmail(registerRequest);
        activationLinkService.sendToken(email);
    }

    @Override
    public void verifyActivationLink(String activationLinkToken) {
        ActivationLink activationLink = activationLinkService.findByToken(activationLinkToken);

        User user = activationLink.getUser();
        user.setEnabled(true);

        userService.save(user);
    }
}
