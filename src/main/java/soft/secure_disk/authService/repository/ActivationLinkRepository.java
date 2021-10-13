package soft.secure_disk.authService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.secure_disk.authService.model.ActivationLink;
import soft.secure_disk.userService.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivationLinkRepository extends JpaRepository<ActivationLink, Integer> {

    Optional<ActivationLink> findByToken(String token);

    boolean existsByUser(User user);

    void deleteByUser(User user);

    List<ActivationLink> deleteAllByExpireAtBefore(LocalDateTime localDateTime);
}
