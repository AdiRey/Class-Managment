package pl.javadev.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "This id is already taken.")
public class DuplicateIdException extends RuntimeException {
}
