package pl.javadev.teacher;

public class TeacherMapper {
    public static TeacherDto map(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setEmail(teacher.getEmail());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setDegree(teacher.getDegree());
        return dto;
    }

    public static Teacher map(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());
        teacher.setEmail(dto.getEmail());
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setDegree(dto.getDegree());
        return teacher;
    }
}