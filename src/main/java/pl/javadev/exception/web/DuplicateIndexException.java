package pl.javadev.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Użytkownik z takim indeksem już istnieje")
public class DuplicateIndexException extends RuntimeException {
    public DuplicateIndexException(String message) {
        super(message);
    }
}
