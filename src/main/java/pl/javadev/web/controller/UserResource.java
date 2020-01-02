package pl.javadev.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.ConflictPasswordException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.user.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    ResponseEntity<UserDto> save(@RequestBody @Valid UserRegistrationDto dto, BindingResult result,
                                 HttpServletResponse response) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            createCookies(dto, response);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid data, please check it again.");
        }

        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An account with existing id cannot be created.");
        UserDto savedUserDto = userService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedUserDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedUserDto);
    }

    @PostMapping("/{id}")
    void savingUnderSpecifiedId(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already taken.");
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> delete(@PathVariable Long id, @RequestBody @Valid UserDeleteDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid data, please check it again.");
        }
        try {
            UserDto userDto = userService.delete(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Wrong password.");
        }
    }

    @DeleteMapping("") // only for owner TODO add a new role "OWNER"
    ResponseEntity<List<UserDto>> deleteAll() {
        List<UserDto> users = userService.deleteAll();
        if (users.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user in this database.");
        return ResponseEntity.ok(users);
    }

    @GetMapping("")
    Page<UserDto> findUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
                            @RequestParam(required = false, defaultValue = "ASC") String sort,
                            @RequestParam(required = false, defaultValue = "") String filter) {
        return userService.findAllUsersUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    UserDto findUserById(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @PutMapping("")
    ResponseEntity<UserDto> edit() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDto> editUser(@PathVariable Long id, @RequestBody @Valid UserDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid data, please check it again.");
        }

        UserDto userDto = userService.editUser(id, dto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}/password")
    ResponseEntity<UserDto> editUserPassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDto dto,
                                             BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid data, please check it again.");
        }
        try {
            UserDto userDto = userService.editPassword(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match.");
        }
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
