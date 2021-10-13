package soft.secure_disk.storageService.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FolderService {

    void copyToFolderStorage(MultipartFile multipartFile, String username) throws FileNotFoundException;

    boolean exists(String filePath);

    boolean create(String filePath);

    boolean delete(String filePath) throws IOException;
}
