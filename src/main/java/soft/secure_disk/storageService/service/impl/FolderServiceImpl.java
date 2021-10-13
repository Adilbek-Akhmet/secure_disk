package soft.secure_disk.storageService.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soft.secure_disk.storageService.model.StorageFolder;
import soft.secure_disk.storageService.service.FolderService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final StorageFolder storageFolder;

    @Override
    public void copyToFolderStorage(MultipartFile multipartFile, String username) throws FileNotFoundException {

        String filePath = storageFolder.getPath() + File.separatorChar + username;

        if (!exists(filePath)) {
            create(filePath);
        }

        Path path = Path.of(filePath);

        Path fileLocation = path.resolve(
                Paths.get(Objects.requireNonNull(multipartFile.getOriginalFilename())))
                .normalize().toAbsolutePath();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, fileLocation,
                    StandardCopyOption.REPLACE_EXISTING);
            log.info("Файл {} загружен папку, расположение файла: {}", multipartFile.getOriginalFilename(), path);
        } catch (IOException e) {
            log.warn("Файл {} НЕ загружен папку", multipartFile.getOriginalFilename());
            throw new FileNotFoundException("Файл НЕ загружен папку");
        }
    }

    @Override
    public boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    @Override
    public boolean create(String filePath) {
        File file = new File(filePath);
        return file.mkdir();
    }

    @Override
    public boolean delete(String filePath) throws IOException {
        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }

        return !file.exists();
    }
}
