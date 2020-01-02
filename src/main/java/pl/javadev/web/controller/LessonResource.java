package pl.javadev.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.exception.other.WrongTimeException;
import pl.javadev.lesson.LessonDto;
import pl.javadev.lesson.LessonService;
import pl.javadev.lesson.LessonStudentsDto;
import pl.javadev.user.UserDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonResource {
    private LessonService lessonService;

    public LessonResource(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("")
    List<LessonDto> findAll() {
        return lessonService.findAllLessons();
    }

    @GetMapping("/{id}")
    ResponseEntity<LessonDto> findLesson(@PathVariable Long id) {
        LessonDto dto = lessonService.findById(id);
        if (dto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("")
    ResponseEntity<LessonDto> save(@RequestBody @Valid LessonDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        }
        if (dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nie można utworzyć zajec które maja id");
        LessonDto savedLesson = lessonService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedLesson.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}")
    void savingSpecifyId(@PathVariable Long id) {
        LessonDto dto = lessonService.findById(id);
        if (dto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    ResponseEntity<LessonDto> editLesson(@PathVariable Long id, @RequestBody @Valid LessonDto dto, BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        if (!id.equals(dto.getId()))
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        LessonDto lessonDto = lessonService.update(dto);
        return ResponseEntity.ok(lessonDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<LessonDto> delete(@PathVariable Long id) {
        try {
            LessonDto lessonDto = lessonService.delete(id);
            if (lessonDto != null) {
                return ResponseEntity.ok(lessonDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (WrongTimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Zajęcia już się odbyły!");
        }
    }

    @PostMapping("/{id}/user")
    ResponseEntity<LessonDto> addUserToLesson(@PathVariable Long id, @RequestBody UserDto dto) {
        LessonDto lessonDto = lessonService.addUsers(id, dto);
        if (lessonDto != null)
            return ResponseEntity.ok(lessonDto);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/students")
    LessonStudentsDto getAllStudents(@PathVariable Long id) {
        return lessonService.getAllStudents(id);
    }

}
