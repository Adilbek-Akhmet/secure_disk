package soft.secure_disk.notificationService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationByEmail {

    private String recipient;
    private String subject;
    private String text;
    private List<MultipartFile> attachments;

    public NotificationByEmail(String recipient, String subject, String text) {
        this.subject = subject;
        this.recipient = recipient;
        this.text = text;
    }


}
