package pl.javadev.user;

public class UserRegistrationMapper {
    public static UserRegistrationDto map(User user) {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setGrade(user.getGrade());
        dto.setMajor(user.getMajor());
        return dto;
    }

    public static User map(UserRegistrationDto dtoReg) {
        User user = new User();
        user.setId(dtoReg.getId());
        user.setEmail(dtoReg.getEmail());
        user.setPassword(dtoReg.getPassword());
        user.setFirstName(dtoReg.getFirstName());
        user.setLastName(dtoReg.getLastName());
        user.setGrade(dtoReg.getGrade());
        user.setMajor(dtoReg.getMajor());
        return user;
    }
}
