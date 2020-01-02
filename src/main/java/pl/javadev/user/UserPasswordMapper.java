package pl.javadev.user;

public class UserPasswordMapper {
    public static User map(UserPasswordDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        return user;
    }
    public static UserPasswordDto map(User user) {
        UserPasswordDto dto = new UserPasswordDto();
        dto.setId(user.getId());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
