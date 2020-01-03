package pl.javadev.lesson;

public class LessonRegistrationMapper {
    public static LessonRegistrationDto map(Lesson lesson) {
        LessonRegistrationDto dto = new LessonRegistrationDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setStart(lesson.getStart());
        dto.setEnd(lesson.getEnd());
        return dto;
    }
    public static Lesson map(LessonRegistrationDto dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setStart(dto.getStart());
        lesson.setEnd(dto.getEnd());
        return lesson;
    }
}
