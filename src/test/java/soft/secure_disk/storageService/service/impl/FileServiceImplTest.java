package soft.secure_disk.storageService.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import soft.secure_disk.storageService.dto.FileRequest;
import soft.secure_disk.storageService.exception.FileExistsInFolderException;
import soft.secure_disk.storageService.model.AppFile;
import soft.secure_disk.storageService.model.StorageFolder;
import soft.secure_disk.storageService.repository.AppFileRepository;
import soft.secure_disk.storageService.service.FolderService;
import soft.secure_disk.userService.model.User;
import soft.secure_disk.userService.service.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl underTest;

    @Mock
    private UserService userService;

    @Mock
    private AppFileRepository appFileRepository;

    @Mock
    private StorageFolder storageFolder;

    @Mock
    private FolderService folderService;

    @Test
    void findAllByUser_Email() {
        String email = "adilbek.akhmet@gmail.com";

        underTest.findAllByUser_Email(email);

        verify(appFileRepository, times(1)).findAllByUser_Email(email);
    }

    @Test
    void save() throws FileNotFoundException {
        String email = "adilbek.akhmet@gmail.com";
        FileRequest fileRequest = new FileRequest(
                new MockMultipartFile("sss", "dssdsd".getBytes(StandardCharsets.UTF_8))
        );

        when(storageFolder.getPath()).thenReturn("c/ddd/ddd");

        underTest.save(fileRequest, email);

        verify(appFileRepository).save(any(AppFile.class));

    }

    @Test
    void saveWithFileExistsInFolderException() throws FileNotFoundException {
        String email = "adilbek.akhmet@gmail.com";
        FileRequest fileRequest = new FileRequest(
                new MockMultipartFile("sss", "dssdsd".getBytes(StandardCharsets.UTF_8))
        );

        when(storageFolder.getPath()).thenReturn("c/ddd/ddd");
        when(folderService.exists(anyString())).thenReturn(true);

        assertThatThrownBy(() -> underTest.save(fileRequest, email))
                .isInstanceOf(FileExistsInFolderException.class)
                .hasMessageContaining("File with such name exists");

        verify(appFileRepository, never()).save(any(AppFile.class));

    }

    @Test
    void delete() throws IOException {
        Integer id = 1;
        AppFile appFile = new AppFile();
        User user = new User();
        user.setEmail("example@example.com");
        appFile.setUser(new User());
        when(appFileRepository.findById(1)).thenReturn(java.util.Optional.of(appFile));
        when(storageFolder.getPath()).thenReturn("c/ddd/ddd");
        underTest.delete(id);
        verify(folderService, times(1)).delete(anyString());
    }
}