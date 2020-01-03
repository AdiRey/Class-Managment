package pl.javadev.teacher;

public class TeacherRegistrationMapper {
    public static TeacherRegistrationDto map(Teacher teacher) {
        TeacherRegistrationDto dto = new TeacherRegistrationDto();
        dto.setId(teacher.getId());
        dto.setEmail(teacher.getEmail());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setDegree(teacher.getDegree());
        return dto;
    }

    public static Teacher map(TeacherRegistrationDto dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());
        teacher.setEmail(dto.getEmail());
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setDegree(dto.getDegree());
        return teacher;
    }
}
