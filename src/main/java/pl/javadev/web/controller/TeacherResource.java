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
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.teacher.TeacherServiceImpl;
import pl.javadev.user.UserDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherResource {
    private TeacherServiceImpl teacherServiceImpl;

    public TeacherResource(TeacherServiceImpl teacherServiceImpl) {
        this.teacherServiceImpl = teacherServiceImpl;
    }

    @GetMapping("")
    Page<TeacherDto> findTeachers(@RequestParam(required = false, defaultValue = "0") final Integer page,
                            @RequestParam(required = false, defaultValue = "ASC") final String sort,
                            @RequestParam(required = false, defaultValue = "") final String filter) {
        return teacherServiceImpl.findAllTeachersUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    ResponseEntity<TeacherDto> findLesson(@PathVariable Long id) {
        TeacherDto dto = teacherServiceImpl.findById(id);
        if (dto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("")
    ResponseEntity<TeacherDto> save(@RequestBody @Valid TeacherDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        }
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nie można utworzyć zajec które maja id");
        TeacherDto savedDto = teacherServiceImpl.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}")
    void savingSpecifyId(@PathVariable Long id) {
        TeacherDto dto = teacherServiceImpl.findById(id);
        if (dto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @PutMapping("")
    void edit() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping("/{id}")
    ResponseEntity<TeacherDto> editLesson(@PathVariable Long id, @RequestBody @Valid TeacherDto dto,
                                        BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        if (!id.equals(dto.getId()))
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        TeacherDto teacherDto = teacherServiceImpl.update(dto);
        return ResponseEntity.ok(teacherDto);
    }
    @DeleteMapping("")
    ResponseEntity<List<TeacherDto>> deleteAll() {
        List<TeacherDto> teachers = teacherServiceImpl.deleteAll();
        if (teachers.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no teacher in this database.");
        return ResponseEntity.ok(teachers);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TeacherDto> delete(@PathVariable Long id) {
        TeacherDto teacherDto = teacherServiceImpl.delete(id);
        if (teacherDto != null) {
            return ResponseEntity.ok(teacherDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler({InvalidIdException.class})
    public void handleInvalidException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with that id doesn't exist.");
    }

    @ExceptionHandler({ConflictIdException.class})
    public void handleConflictException() {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
    }
}
