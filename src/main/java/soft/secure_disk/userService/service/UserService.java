package soft.secure_disk.userService.service;

import soft.secure_disk.userService.model.User;

public interface UserService {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    void save(User user);
}
