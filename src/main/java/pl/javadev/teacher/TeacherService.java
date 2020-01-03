package pl.javadev.teacher;

import java.util.List;

public interface TeacherService {
    TeacherDto findById(Long id);
    TeacherDto save(TeacherDto dto);
    TeacherDto update(TeacherDto dto);
    TeacherDto delete(Long id);
}
