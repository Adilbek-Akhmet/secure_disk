package soft.secure_disk.authService.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import soft.secure_disk.authService.model.ActivationLink;
import soft.secure_disk.authService.repository.ActivationLinkRepository;
import soft.secure_disk.notificationService.model.NotificationByEmail;
import soft.secure_disk.notificationService.service.MailNotificationService;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ActivationLinkServiceImplTest {

    @InjectMocks
    private ActivationLinkServiceImpl underTest;

    @Mock
    private ActivationLinkRepository activationLinkRepository;

    @Mock
    private MailNotificationService mailNotificationService;

    @Mock
    private UserService userService;

    @Test
    void sendToken() {
        User user = new User();
        user.setEmail("adilbek.akhmet@gmail.com");

        when(userService.existsByEmail(user.getEmail())).thenReturn(true);

        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        underTest.sendToken(user.getEmail());

        verify(mailNotificationService, times(1)).sendMessage(any(NotificationByEmail.class));
        verify(activationLinkRepository, times(1)).save(any(ActivationLink.class));
    }

    @Test
    void generateToken() {
        String token = underTest.generateToken();
        assertThat(token).isNotNull();
    }

    @Test
    void findByToken() {
        ActivationLink activationLink = new ActivationLink();
        activationLink.setToken("token");

        when(activationLinkRepository.findByToken(activationLink.getToken()))
                .thenReturn(Optional.of(activationLink));

        ActivationLink actual = underTest.findByToken(activationLink.getToken());

        assertEquals(activationLink, actual);
    }

    @Test
    void findByTokenWithException() {
        ActivationLink activationLink = new ActivationLink();
        activationLink.setToken("token");

        when(activationLinkRepository.findByToken(activationLink.getToken()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findByToken(activationLink.getToken()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid token");
    }

    @Test
    void deleteByUser() {
        User user = new User();

        underTest.deleteByUser(user);

        verify(activationLinkRepository, times(1)).deleteByUser(user);
    }
}