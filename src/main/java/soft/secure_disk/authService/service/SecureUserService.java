package soft.secure_disk.authService.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import soft.secure_disk.authService.dto.RegisterRequest;

public interface SecureUserService extends UserDetailsService {

    String saveNotActivatedEmail(RegisterRequest registerRequest);

}
