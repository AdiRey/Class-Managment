package pl.javadev.lesson;

import pl.javadev.user.UserMapper;

import java.util.stream.Collectors;

public class LessonMapper {
    public static LessonDto entityToDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setStart(lesson.getStart());
        dto.setEnd(lesson.getEnd());
        return dto;
    }
    public static Lesson dtoToEntity(LessonDto dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setStart(dto.getStart());
        lesson.setEnd(dto.getEnd());
        return lesson;
    }

    public static LessonStudDto entityToStudDto(Lesson lesson) {
        LessonStudDto dto = new LessonStudDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setStart(lesson.getStart());
        dto.setEnd(lesson.getEnd());
        dto.setUsers(lesson.getUsers().stream().map(UserMapper::entityToDto).collect(Collectors.toList()));
        return dto;
    }

    @Deprecated
    public static Lesson studDtoToEntity(LessonStudDto dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setStart(dto.getStart());
        lesson.setEnd(dto.getEnd());
        lesson.setUsers(dto.getUsers().stream().map(UserMapper::dtoToEntity).collect(Collectors.toList()));
        return lesson;
    }
}
