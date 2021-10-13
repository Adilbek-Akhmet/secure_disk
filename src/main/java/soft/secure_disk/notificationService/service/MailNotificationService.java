package soft.secure_disk.notificationService.service;


import soft.secure_disk.notificationService.model.NotificationByEmail;

public interface MailNotificationService {

    void sendMessage(NotificationByEmail notificationByEmail);

//    void sendMessageWithAttachments(NotificationByEmail notificationByEmail);

}
