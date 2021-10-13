package soft.secure_disk.storageService.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage.folder")
public class StorageFolder {
    private String path;
}
