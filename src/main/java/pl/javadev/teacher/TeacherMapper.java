package pl.javadev.teacher;

public class TeacherMapper {
    public static TeacherDto toDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setEmail(teacher.getEmail());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setDegree(teacher.getDegree());
        return dto;
    }
}