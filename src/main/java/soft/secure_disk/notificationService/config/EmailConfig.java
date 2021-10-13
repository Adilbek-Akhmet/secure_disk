package soft.secure_disk.notificationService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {

    private String username;

}
