package pl.javadev.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.teacher.TeacherService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherResource {
    private TeacherService teacherService;

    public TeacherResource(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("")
    List<TeacherDto> findAll() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    ResponseEntity<TeacherDto> findLesson(@PathVariable Long id) {
        TeacherDto dto = teacherService.findById(id);
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
        TeacherDto savedDto = teacherService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}")
    void savingSpecifyId(@PathVariable Long id) {
        TeacherDto dto = teacherService.findById(id);
        if (dto == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    ResponseEntity<TeacherDto> editLesson(@PathVariable Long id, @RequestBody @Valid TeacherDto dto,
                                        BindingResult result) {
        if (result.hasErrors())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sprawdź poprawność wprowadzonych danych");
        if (!id.equals(dto.getId()))
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
        TeacherDto teacherDto = teacherService.update(dto);
        return ResponseEntity.ok(teacherDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TeacherDto> delete(@PathVariable Long id) {
        TeacherDto teacherDto = teacherService.delete(id);
        if (teacherDto != null) {
            return ResponseEntity.ok(teacherDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
