package pl.javadev.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Istnieje juz obiekt o takim ID!")
public class DifferentIdException extends RuntimeException {
    public DifferentIdException() {
    }

    public DifferentIdException(String message) {
        super(message);
    }

    public DifferentIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DifferentIdException(Throwable cause) {
        super(cause);
    }

    public DifferentIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
