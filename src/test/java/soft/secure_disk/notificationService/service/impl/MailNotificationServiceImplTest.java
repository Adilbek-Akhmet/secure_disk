package soft.secure_disk.notificationService.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import soft.secure_disk.notificationService.config.EmailConfig;
import soft.secure_disk.notificationService.model.NotificationByEmail;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MailNotificationServiceImplTest {

    @InjectMocks
    private MailNotificationServiceImpl underTest;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailConfig emailConfig;

    @Test
    void sendMessage() {
        NotificationByEmail notificationByEmail = new NotificationByEmail();
        notificationByEmail.setSubject("Hello");
        notificationByEmail.setRecipient("example@example.com");
        notificationByEmail.setText("Text");

        when(emailConfig.getUsername()).thenReturn("adilbek.akhmet@gmail.com");

        underTest.sendMessage(notificationByEmail);
    }
}






