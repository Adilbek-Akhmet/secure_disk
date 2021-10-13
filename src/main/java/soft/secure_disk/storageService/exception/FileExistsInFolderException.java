package soft.secure_disk.storageService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileExistsInFolderException extends RuntimeException {

    public FileExistsInFolderException(String message) {
        super(message);
    }
}
