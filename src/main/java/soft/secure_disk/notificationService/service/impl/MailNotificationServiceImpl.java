package soft.secure_disk.notificationService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soft.secure_disk.notificationService.config.EmailConfig;
import soft.secure_disk.notificationService.model.NotificationByEmail;
import soft.secure_disk.notificationService.service.MailNotificationService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailNotificationServiceImpl implements MailNotificationService {

    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;

    @Override
    public void sendMessage(NotificationByEmail notificationByEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailConfig.getUsername());
            messageHelper.setTo(notificationByEmail.getRecipient());
            messageHelper.setSubject(notificationByEmail.getSubject());
            messageHelper.setText(notificationByEmail.getText());
        };

        sendToEmail(mimeMessagePreparator);
    }

//    @Override
//    public void sendMessageWithAttachments(NotificationByEmail notificationByEmail) {
//        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom(emailConfig.getUsername());
//            messageHelper.setTo(notificationByEmail.getRecipient());
//            messageHelper.setSubject(notificationByEmail.getSubject());
//            messageHelper.setText(notificationByEmail.getText());
//
//            List<MultipartFile> attachments = notificationByEmail.getAttachments();
//            for (MultipartFile attachment: attachments) {
//                messageHelper.addAttachment(
//                        Objects.requireNonNull(attachment.getOriginalFilename()),
//                        attachment
//                );
//            }
//        };
//
//        sendToEmail(mimeMessagePreparator);
//    }

    private void sendToEmail(MimeMessagePreparator mimeMessagePreparator) {
        try {
            javaMailSender.send(mimeMessagePreparator);
        } catch (MailSendException e) {
            throw new MailSendException("Mail Exception occurred when sending message");
        }
    }
}
