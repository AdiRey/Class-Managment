package pl.javadev.web.service;

import org.springframework.data.domain.Page;
import pl.javadev.lesson.LessonDto;
import pl.javadev.lesson.LessonRegistrationDto;
import pl.javadev.teacher.TeacherDto;

import java.util.List;

public interface LessonService {
    Page<LessonDto> findAllLessonsUsingPaging(int numberOfPage, String sortText, String text);
    LessonDto addUsers(Long id, Long userId);
    LessonDto addTeacher(Long id, TeacherDto teacherDto);
    LessonDto findLesson(Long id);
    LessonDto save(LessonRegistrationDto dto);
    LessonDto findById(Long id);
    LessonDto editLesson(Long id, LessonRegistrationDto dto);
    LessonDto delete(Long id);
    List<LessonDto> deleteAll();
}
