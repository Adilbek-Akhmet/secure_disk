package soft.secure_disk.storageService.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.secure_disk.storageService.dto.FileRequest;
import soft.secure_disk.storageService.exception.FileExistsInFolderException;
import soft.secure_disk.storageService.model.AppFile;
import soft.secure_disk.storageService.model.StorageFolder;
import soft.secure_disk.storageService.repository.AppFileRepository;
import soft.secure_disk.storageService.service.FileService;
import soft.secure_disk.storageService.service.FolderService;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UserService userService;
    private final AppFileRepository appFileRepository;
    private final StorageFolder storageFolder;
    private final FolderService folderService;

    @Override
    public List<AppFile> findAllByUser_Email(String email) {
        return appFileRepository.findAllByUser_Email(email);
    }

    @Override
    public void save(FileRequest fileRequest, String username) throws FileNotFoundException {
        User user = userService.findByEmail(username);

        AppFile appFile = AppFile.builder()
                .user(user)
                .fileName(fileRequest.getFile().getOriginalFilename())
                .createdAt(LocalDate.now())
                .size(fileRequest.getFile().getSize())
                .build();

        String filePath = storageFolder.getPath()
                .concat(File.separatorChar + username + File.separatorChar + appFile.getFileName());

        if (!folderService.exists(filePath)) {
            folderService.copyToFolderStorage(fileRequest.getFile(), username);
        } else {
            throw new FileExistsInFolderException("File with such name exists");
        }

        appFileRepository.save(appFile);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws IOException {
        AppFile appFile = appFileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File Not Found"));
        String fileLocation = storageFolder.getPath()
                .concat(File.separatorChar + appFile.getUser().getUsername() + File.separatorChar + appFile.getFileName());
        Path filePath = Path.of(fileLocation);
        boolean isDeleted = folderService.delete(filePath.toString());

        if (isDeleted) {
            appFileRepository.delete(appFile);
        }
    }
}
