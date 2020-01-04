package pl.javadev.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.ConflictPasswordException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.user.UserDeleteDto;
import pl.javadev.user.UserDto;
import pl.javadev.user.UserPasswordDto;
import pl.javadev.user.UserRegistrationDto;
import pl.javadev.web.service.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserResource {
    private UserServiceImpl userServiceImpl;

    public UserResource(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("")
    ResponseEntity<UserDto> save(@RequestBody @Valid final UserRegistrationDto dto) {
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An account with existing id cannot be created.");
        UserDto savedUserDto = userServiceImpl.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedUserDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedUserDto);
    }

    @PostMapping("/{id}")
    void savingUnderSpecifiedId(@PathVariable final Long id) {
        UserDto user = userServiceImpl.findById(id);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already taken.");
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> delete(@PathVariable final Long id, @RequestBody @Valid final UserDeleteDto dto) {
        try {
            UserDto userDto = userServiceImpl.delete(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Wrong password.");
        }
    }

    @DeleteMapping("") // only for owner TODO add a new role "OWNER"
    ResponseEntity<List<UserDto>> deleteAll() {
        List<UserDto> users = userServiceImpl.deleteAll();
        if (users.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user in this database.");
        return ResponseEntity.ok(users);
    }

    @GetMapping("")
    List<UserDto> findUsers(@RequestParam(required = false, defaultValue = "0") final Integer page,
                            @RequestParam(required = false, defaultValue = "ASC") final String sort,
                            @RequestParam(required = false, defaultValue = "") final String filter) {
        return userServiceImpl.findAllUsersUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    UserDto findUserById(@PathVariable final Long id) {
        return userServiceImpl.findUser(id);
    }

    @PutMapping("")
    void edit() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDto> editUser(@PathVariable final Long id, @RequestBody @Valid final UserDto dto) {
        UserDto userDto = userServiceImpl.editUser(id, dto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}/password")
    ResponseEntity<UserDto> editUserPassword(@PathVariable final Long id, @RequestBody @Valid final UserPasswordDto dto) {
        try {
            UserDto userDto = userServiceImpl.editPassword(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match.");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({InvalidIdException.class})
    public void handleInvalidException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
    }

    @ExceptionHandler({ConflictIdException.class})
    public void handleConflictException() {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
    }

    private void createCookies(UserRegistrationDto dto, HttpServletResponse response) {
        List<Cookie> cookies = new ArrayList<>(5);
        cookies.add(new Cookie("email",dto.getEmail()));
        cookies.add(new Cookie("firstName", dto.getFirstName()));
        cookies.add(new Cookie("lastName", dto.getLastName()));
        cookies.add(new Cookie("grade", dto.getGrade()));
        cookies.add(new Cookie("major", dto.getMajor()));

        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
