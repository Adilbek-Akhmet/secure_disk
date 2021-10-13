package soft.secure_disk.storageService.service;


import soft.secure_disk.storageService.dto.FileRequest;
import soft.secure_disk.storageService.model.AppFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {

    List<AppFile> findAllByUser_Email(String email);

    void save(FileRequest fileRequest, String username) throws FileNotFoundException;

    void delete(Integer id) throws IOException;

}
