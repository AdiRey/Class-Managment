package pl.javadev.web.service;

import org.springframework.data.domain.Page;
import pl.javadev.teacher.TeacherDto;

import java.util.List;

public interface TeacherService {
    TeacherDto save(TeacherDto dto);
    Page<TeacherDto> findAllTeachersUsingPaging(int numberOfPage, String sortText, String text);
    TeacherDto edit(Long id, TeacherDto dto);
    TeacherDto delete(Long id);
    List<TeacherDto> deleteAll();
    TeacherDto findTeacher(Long id);
}
