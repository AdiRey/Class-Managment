package pl.javadev.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.exception.other.WrongTimeException;
import pl.javadev.lesson.LessonDto;
import pl.javadev.lesson.LessonRegistrationDto;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.user.UserDto;
import pl.javadev.web.service.LessonServiceImpl;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lessons")
public class LessonResource {
    private LessonServiceImpl lessonServiceImpl;

    public LessonResource(LessonServiceImpl lessonServiceImpl) {
        this.lessonServiceImpl = lessonServiceImpl;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    Page<LessonDto> findLessons(@RequestParam(required = false, defaultValue = "0") final Integer page,
                                @RequestParam(required = false, defaultValue = "ASC") final String sort,
                                @RequestParam(required = false, defaultValue = "") final String filter) {
        return lessonServiceImpl.findAllLessonsUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN', 'ROLE_USER'})")
    LessonDto findLessonById(@PathVariable final Long id) {
        try {
            return lessonServiceImpl.findLesson(id);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
        }
   }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<LessonDto> save(@RequestBody @Valid final LessonRegistrationDto dto) {
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A lesson with existing id cannot be created.");
        LessonDto savedLessonDto = lessonServiceImpl.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedLessonDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedLessonDto);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void savingUnderSpecifiedId(@PathVariable final Long id) {
        LessonDto lessonDto = lessonServiceImpl.findById(id);
        if (lessonDto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already taken.");
    }

    @PostMapping("/{id}/addU")
    @PreAuthorize("(#dto.email.equals(principal.username) or #dto.indexNumber" +
            ".equals(principal.username) or hasRole('ROLE_ADMIN'))")
    ResponseEntity<LessonDto> addUserToLesson(@PathVariable Long id, @RequestParam UserDto dto) {
        try {
            LessonDto lessonDto = lessonServiceImpl.addUsers(id, dto.getId());
            return ResponseEntity.ok(lessonDto);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
        }
    }

    @PostMapping("/{id}/addT")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<LessonDto> addTeacherToLesson(@PathVariable Long id, @RequestBody TeacherDto dto) {
        try {
            LessonDto lessonDto = lessonServiceImpl.addTeacher(id, dto);
            return ResponseEntity.ok(lessonDto);
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<LessonDto> editLesson(@PathVariable Long id, @RequestBody @Valid LessonRegistrationDto dto) {
        try {
            LessonDto lessonDto = lessonServiceImpl.editLesson(id, dto);
            return ResponseEntity.ok(lessonDto);
        } catch (ConflictIdException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with that id doesn't exist.");
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void edit() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<LessonDto> delete(@PathVariable final Long id) {
        try {
            LessonDto dto = lessonServiceImpl.delete(id);
            return ResponseEntity.ok(dto);
        } catch (WrongTimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "You cannot delete the lesson if it already started.");
        } catch (InvalidIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson with that id doesn't exist.");
        }
    }

    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<LessonDto>> deleteAll() {
        List<LessonDto> lessons = lessonServiceImpl.deleteAll();
        if (lessons.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no lesson to delete in this database.");
        return ResponseEntity.ok(lessons);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleLocalDateTimeFormatException() {
        return "Correct format: yyyy-MM-dd'T'hh:mm:ss";
    }
}
