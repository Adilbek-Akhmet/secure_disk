package soft.secure_disk.storageService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.secure_disk.storageService.model.AppFile;

import java.util.List;

public interface AppFileRepository extends JpaRepository<AppFile, Integer> {

    List<AppFile> findAllByUser_Email(String username);
}
