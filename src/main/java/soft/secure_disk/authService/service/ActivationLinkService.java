package soft.secure_disk.authService.service;


import soft.secure_disk.authService.model.ActivationLink;
import soft.secure_disk.userService.model.User;

public interface ActivationLinkService {

    void sendToken(String email);

    String generateToken();

    ActivationLink findByToken(String activationLinkToken);

    void deleteByUser(User user);

}
