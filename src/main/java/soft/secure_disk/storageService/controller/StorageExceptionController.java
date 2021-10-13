package soft.secure_disk.storageService.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import soft.secure_disk.storageService.exception.FileExistsInFolderException;

@ControllerAdvice
public class StorageExceptionController {

    @ExceptionHandler({FileExistsInFolderException.class})
    public String handleFileExistsInFolderException(RedirectAttributes redirectAttributes,
                                                    FileExistsInFolderException exception) {
        redirectAttributes.addFlashAttribute("exception", exception.getMessage());
        return "redirect:/disk/upload";
    }

}
