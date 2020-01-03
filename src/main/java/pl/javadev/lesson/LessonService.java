package pl.javadev.lesson;

import org.springframework.data.domain.Page;
import pl.javadev.user.UserDto;

import java.util.List;

public interface LessonService {
    Page<LessonDto> findAllLessonsUsingPaging(int numberOfPage, String sortText, String text);
    LessonDto addUsers(Long id, UserDto userDto);
    LessonDto findLesson(Long id);
    LessonDto save(LessonRegistrationDto dto);
    LessonDto findById(Long id);
    LessonDto editLesson(Long id, LessonRegistrationDto dto);
    LessonDto delete(Long id);
    List<LessonDto> deleteAll();
}
