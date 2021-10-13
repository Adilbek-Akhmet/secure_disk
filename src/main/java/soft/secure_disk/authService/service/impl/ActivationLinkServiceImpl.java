package soft.secure_disk.authService.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.secure_disk.authService.model.ActivationLink;
import soft.secure_disk.authService.repository.ActivationLinkRepository;
import soft.secure_disk.authService.service.ActivationLinkService;
import soft.secure_disk.notificationService.model.NotificationByEmail;
import soft.secure_disk.notificationService.service.MailNotificationService;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ActivationLinkServiceImpl implements ActivationLinkService {

    private final ActivationLinkRepository activationLinkRepository;
    private final MailNotificationService mailNotificationService;
    private final UserService userService;

    @Override
    @Async
    @Transactional
    public void sendToken(String email) {
       if (userService.existsByEmail(email)) {
           User user = userService.findByEmail(email);

           if (activationLinkRepository.existsByUser(user)) {
               activationLinkRepository.deleteByUser(user);
           }
           String token = generateToken();

           mailNotificationService.sendMessage(
                   new NotificationByEmail(
                           user.getUsername(),
                           "Activate Link",
                           "http://localhost:8080/auth/token/" + token
                   )
           );

           activationLinkRepository.save(
                   new ActivationLink(token, LocalDateTime.now().plusMinutes(30), user)
           );
           log.info("Activation sent");
       }
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public ActivationLink findByToken(String token) {
        return activationLinkRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        activationLinkRepository.deleteByUser(user);
    }
}
