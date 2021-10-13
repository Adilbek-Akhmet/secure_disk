package soft.secure_disk.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft.secure_disk.userService.model.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndEnabled(String email, boolean enabled);

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

}
