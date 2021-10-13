package soft.secure_disk.authService.service;

import soft.secure_disk.authService.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest registerRequest);

    void verifyActivationLink(String activationLinkToken);

}
