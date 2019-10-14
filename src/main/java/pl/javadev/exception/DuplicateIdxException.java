package pl.javadev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Użytkownik z takim indeksem już istnieje")
public class DuplicateIdxException extends RuntimeException {
    public DuplicateIdxException(String message) {
        super(message);
    }
}
