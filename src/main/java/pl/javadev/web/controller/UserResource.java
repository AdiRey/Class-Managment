package pl.javadev.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.user.UserDto;
import pl.javadev.user.UserDtoReg;
import pl.javadev.user.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    List<UserDto> findAll() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    ResponseEntity<UserDto> save(@RequestBody @Valid UserDtoReg user, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        }
        if (user.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nie można utworzyć użytkownika który ma id");
        UserDto savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDto> editAccount(@PathVariable Long id, @RequestBody @Valid UserDtoReg user, BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        if (!id.equals(user.getId()))
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        UserDto userDto = userService.update(user);
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> delete(@PathVariable Long id) {
        UserDto userDto = userService.delete(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}/password")
    ResponseEntity<UserDto> editPassword(@PathVariable Long id, @RequestParam String pass) {
        UserDto userDto = userService.editPassword(id, pass);
        if (userDto == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(userDto);
    }
}
