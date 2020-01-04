package pl.javadev.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.ConflictPasswordException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.lesson.LessonDto;
import pl.javadev.lesson.LessonRegistrationDto;
import pl.javadev.web.service.LessonServiceImpl;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.user.UserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lessons")
public class LessonResource {
    private LessonServiceImpl lessonServiceImpl;

    public LessonResource(LessonServiceImpl lessonServiceImpl) {
        this.lessonServiceImpl = lessonServiceImpl;
    }

    @GetMapping("")
    Page<LessonDto> findLessons(@RequestParam(required = false, defaultValue = "0") final Integer page,
                                @RequestParam(required = false, defaultValue = "ASC") final String sort,
                                @RequestParam(required = false, defaultValue = "") final String filter) {
        return lessonServiceImpl.findAllLessonsUsingPaging(page, sort, filter);
    }

    @GetMapping("/{id}")
    LessonDto findLessonById(@PathVariable final Long id) {
        return lessonServiceImpl.findLesson(id);
    }

    @PostMapping("")
    ResponseEntity<LessonDto> save(@RequestBody @Valid final LessonRegistrationDto dto) {
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A lesson with existing id cannot be created.");
        LessonDto savedLessonDto = lessonServiceImpl.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(savedLessonDto.getId()).toUri();
        return ResponseEntity.created(location).body(savedLessonDto);
    }

    @PostMapping("/{id}")
    void savingUnderSpecifiedId(@PathVariable final Long id) {
        LessonDto lessonDto = lessonServiceImpl.findById(id);
        if (lessonDto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
        throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already taken.");
    }

    @PostMapping("/{id}/user")
    ResponseEntity<LessonDto> addUserToLesson(@PathVariable Long id, @RequestBody UserDto dto) {
        LessonDto lessonDto = lessonServiceImpl.addUsers(id, dto);
        if (lessonDto != null)
            return ResponseEntity.ok(lessonDto);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/teacher")
    ResponseEntity<LessonDto> addTeacherToLesson(@PathVariable Long id, @RequestBody TeacherDto dto) {
        LessonDto lessonDto = lessonServiceImpl.addTeacher(id, dto);
        if (lessonDto != null)
            return ResponseEntity.ok(lessonDto);
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<LessonDto> editLesson(@PathVariable Long id, @RequestBody @Valid LessonRegistrationDto dto) {
        LessonDto lessonDto = lessonServiceImpl.editLesson(id, dto);
        return ResponseEntity.ok(lessonDto);
    }

    @PutMapping("")
    void edit() {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<LessonDto> delete(@PathVariable final Long id) {
        try {
            LessonDto dto = lessonServiceImpl.delete(id);
            return ResponseEntity.ok(dto);
        } catch (ConflictPasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Wrong password.");
        }
    }

    @DeleteMapping("")
    ResponseEntity<List<LessonDto>> deleteAll() {
        List<LessonDto> lessons = lessonServiceImpl.deleteAll();
        if (lessons.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user in this database.");
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

    @ExceptionHandler({InvalidIdException.class})
    public void handleInvalidException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with that id doesn't exist.");
    }

    @ExceptionHandler({ConflictIdException.class})
    public void handleConflictException() {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Id doesn't match.");
    }

    private void createCookies(LessonRegistrationDto dto, HttpServletResponse response) {
        List<Cookie> cookies = new ArrayList<>(5);
        cookies.add(new Cookie("title",dto.getTitle()));
        cookies.add(new Cookie("description", dto.getDescription()));

        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
