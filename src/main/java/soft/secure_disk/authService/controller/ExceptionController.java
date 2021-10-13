package soft.secure_disk.authService.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import soft.secure_disk.authService.exception.PasswordsNotMatchException;
import soft.secure_disk.authService.exception.UserWithSuchEmailFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({UserWithSuchEmailFoundException.class})
    public String handleUserWithSuchEmailFoundException(RedirectAttributes redirectAttributes,
                                           UserWithSuchEmailFoundException exception) {
        redirectAttributes.addFlashAttribute("exception", exception.getMessage());
        return "redirect:/auth/register";
    }

    @ExceptionHandler({PasswordsNotMatchException.class})
    public String handlePasswordsNotMatchException(RedirectAttributes redirectAttributes,
                                           PasswordsNotMatchException exception) {
        redirectAttributes.addFlashAttribute("exception", exception.getMessage());
        return "redirect:/auth/register";
    }
}
