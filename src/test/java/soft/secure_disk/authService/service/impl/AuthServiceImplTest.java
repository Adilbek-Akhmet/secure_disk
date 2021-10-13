package soft.secure_disk.authService.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import soft.secure_disk.authService.dto.RegisterRequest;
import soft.secure_disk.authService.model.ActivationLink;
import soft.secure_disk.authService.service.ActivationLinkService;
import soft.secure_disk.authService.service.SecureUserService;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl underTest;

    @Mock
    private UserService userService;

    @Mock
    private SecureUserService secureUserService;

    @Mock
    private ActivationLinkService activationLinkService;

    @Test
    void registerSaveNotActivatedEmail() {
        RegisterRequest registerRequest =
                new RegisterRequest("adilbek.akhmet@gmail.com", "4567Qq@7854", "4567Qq@7854");
        underTest.register(registerRequest);
        verify(secureUserService, times(1)).saveNotActivatedEmail(registerRequest);
    }

    @Test
    void registerSendToken() {
        RegisterRequest registerRequest =
                new RegisterRequest("adilbek.akhmet@gmail.com", "4567Qq@7854", "4567Qq@7854");

        when(secureUserService.saveNotActivatedEmail(registerRequest)).thenReturn(registerRequest.getEmail());

        underTest.register(registerRequest);

        verify(activationLinkService, times(1)).sendToken(registerRequest.getEmail());

    }

    @Test
    void verifyActivationLink() {
        ActivationLink activationLink = new ActivationLink();
        activationLink.setToken("token");
        activationLink.setExpireAt(LocalDateTime.now().plusMinutes(5));
        activationLink.setUser(new User());

        when(activationLinkService.findByToken(activationLink.getToken()))
                .thenReturn(activationLink);

        underTest.verifyActivationLink(activationLink.getToken());

        verify(userService, times(1)).save(activationLink.getUser());

    }
}