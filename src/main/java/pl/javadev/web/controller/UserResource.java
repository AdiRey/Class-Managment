package pl.javadev.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    private UserServiceImpl userServiceImpl;

    public UserResource(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("")
    ResponseEntity<UserDto> save(@RequestBody @Valid final UserRegistrationDto dto) {
        System.out.println("save");
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An account with existing id cannot be created.");
        UserDto savedUserDto = userServiceImpl.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedUserDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedUserDto);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    void savingUnderSpecifiedId(@PathVariable final Long id) {
        try {
            System.out.println("savingUnder");
            userServiceImpl.findUser(id);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already taken.");
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(#dto.email.equals(principal.username) or #dto.indexNumber" +
            ".equals(principal.username) or hasRole('ROLE_ADMIN'))")
    ResponseEntity<UserDto> delete(@PathVariable final Long id, @RequestBody @Valid final UserDeleteDto dto) {
        try {
            System.out.println("delete");
            UserDto userDto = userServiceImpl.delete(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Wrong password.");
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        } catch (ConflictIdException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
        }
    }

    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<UserDto>> deleteAll() {
        System.out.println("deleteAll");
        List<UserDto> users = userServiceImpl.deleteAll();
        if (users.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user in this database.");
        return ResponseEntity.ok(users);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<UserDto> findUsers(@RequestParam(required = false, defaultValue = "0") final Integer page,
                            @RequestParam(required = false, defaultValue = "ASC") final String sort,
                            @RequestParam(required = false, defaultValue = "") final String filter) {
        System.out.println("findUsers");
        return userServiceImpl.findAllUsersUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    @PostAuthorize("(returnObject.email.equals(principal.username) or returnObject.indexNumber" +
            ".equals(principal.username) or hasRole('ROLE_ADMIN'))")
    UserDto findUserById(@PathVariable final Long id) {
        try {
            System.out.println("findUserBy");
            return userServiceImpl.findUser(id);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    void edit() {
        System.out.println("edit");
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("(#dto.email.equals(principal.username) or #dto.indexNumber" +
            ".equals(principal.username) or hasRole('ROLE_ADMIN'))")
    ResponseEntity<UserDto> editUser(@PathVariable final Long id, @RequestBody @Valid final UserDto dto) {
        try {
            System.out.println("editUser");
            UserDto userDto = userServiceImpl.editUser(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        } catch (ConflictIdException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
        }
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #dto.email.equals(principal.username) or #dto.indexNumber.equals" +
            "(principal.username)")
    ResponseEntity<UserDto> editUserPassword(@PathVariable final Long id, @RequestBody @Valid final UserPasswordDto dto) {
        try {
            System.out.println("editUserPassword");
            UserDto userDto = userServiceImpl.editPassword(id, dto);
            return ResponseEntity.ok(userDto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match.");
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        } catch (ConflictIdException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
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
}
