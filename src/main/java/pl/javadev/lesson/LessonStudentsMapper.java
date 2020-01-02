package pl.javadev.lesson;

import org.springframework.stereotype.Component;
import pl.javadev.user.UserMapper;

import java.util.stream.Collectors;

@Component
public class LessonStudentsMapper {
    private UserMapper userMapper;

    public LessonStudentsMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public LessonStudentsDto map(Lesson lesson) {
        LessonStudentsDto dto = new LessonStudentsDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setStart(lesson.getStart());
        dto.setEnd(lesson.getEnd());
        dto.setTeacher(lesson.getTeacher());
        dto.setUsers(lesson.getUsers().stream().map(userMapper::map).collect(Collectors.toList()));
        return dto;
    }

    @Deprecated
    public Lesson map(LessonStudentsDto dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setStart(dto.getStart());
        lesson.setEnd(dto.getEnd());
        lesson.setTeacher(dto.getTeacher());
        lesson.setUsers(dto.getUsers().stream().map(userMapper::map).collect(Collectors.toList()));
        return lesson;
    }
}
