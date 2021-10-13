package soft.secure_disk.authService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Email(message = "Email is required")
    private String email;


    @Pattern(regexp = "^[^\\s].+[^\\s]$", message = "Password can not start with whitespace and end with whitespace")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$",
            message = "Minimum of 1 lower case, 1 upper case, 1 numeric, 1 special character")
    @Size(min = 8, message = "Passwords must be at least 8 characters in length, but can be much longer")
    private String password;

    private String retypePassword;

}
