package pl.javadev.exception.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There is a mistake in repeated password.")
public class DifferentPasswordException extends RuntimeException{
}
