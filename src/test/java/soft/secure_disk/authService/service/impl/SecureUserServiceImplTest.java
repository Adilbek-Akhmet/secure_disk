package soft.secure_disk.authService.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import soft.secure_disk.authService.dto.RegisterRequest;
import soft.secure_disk.authService.service.ActivationLinkService;
import soft.secure_disk.userService.model.Role;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SecureUserServiceImplTest {

    @InjectMocks
    private SecureUserServiceImpl underTest;

    @Mock
    private UserService userService;

    @Mock
    private ActivationLinkService activationLinkService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void saveNotActivatedEmail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email");
        registerRequest.setPassword("password");

        User user = new User();
        user.setEnabled(false);



        when(userService.existsByEmail(registerRequest.getEmail())).thenReturn(true);
        when(userService.findByEmail(registerRequest.getEmail())).thenReturn(user);

        underTest.saveNotActivatedEmail(registerRequest);

//        verify(activationLinkService, times(1)).deleteByUser(user);
//        verify(userService, times(1)).deleteByEmail(registerRequest.getEmail());
        verify(userService, times(1)).save(user);



    }

    @Test
    void loadUserByUsername() {
    }
}