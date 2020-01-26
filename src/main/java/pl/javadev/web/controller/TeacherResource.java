package pl.javadev.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.web.service.TeacherServiceImpl;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherResource {
    private TeacherServiceImpl teacherServiceImpl;

    public TeacherResource(TeacherServiceImpl teacherServiceImpl) {
        this.teacherServiceImpl = teacherServiceImpl;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    Page<TeacherDto> findTeachers(@RequestParam(required = false, defaultValue = "0") final Integer page,
                            @RequestParam(required = false, defaultValue = "ASC") final String sort,
                            @RequestParam(required = false, defaultValue = "") final String filter) {
        return teacherServiceImpl.findAllTeachersUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    TeacherDto findTeacherById(@PathVariable Long id) {
        try {
            return teacherServiceImpl.findTeacher(id);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
        }

    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<TeacherDto> save(@RequestBody @Valid TeacherDto dto) {
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot add teacher with existing id.");
        TeacherDto savedDto = teacherServiceImpl.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void savingSpecifyId(@PathVariable Long id) {
        try {
            teacherServiceImpl.findTeacher(id);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already taken.");
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void edit() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<TeacherDto> editTeacher(@PathVariable Long id, @RequestBody @Valid TeacherDto dto) {
        try {
            TeacherDto teacherDto = teacherServiceImpl.edit(id, dto);
            return ResponseEntity.ok(teacherDto);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
        } catch (ConflictIdException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
        }
    }
    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<TeacherDto>> deleteAll() {
        List<TeacherDto> teachers = teacherServiceImpl.deleteAll();
        if (teachers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no teacher in this database.");
        return ResponseEntity.ok(teachers);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<TeacherDto> delete(@PathVariable Long id) {
        try {
            TeacherDto teacherDto = teacherServiceImpl.delete(id);
            return ResponseEntity.ok(teacherDto);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
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
