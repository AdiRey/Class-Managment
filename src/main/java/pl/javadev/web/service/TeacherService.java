package pl.javadev.web.service;

import org.springframework.data.domain.Page;
import pl.javadev.teacher.TeacherDto;

import java.util.List;

public interface TeacherService {
    Page<TeacherDto> findAllTeachersUsingPaging(int numberOfPage, String sortText, String text);
    TeacherDto findById(Long id);
    TeacherDto save(TeacherDto dto);
    TeacherDto edit(TeacherDto dto);
    TeacherDto delete(Long id);
    List<TeacherDto> deleteAll();
}
