package pl.javadev.user;

public class UserPasswordMapper {
    public static User map(UserPasswordDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setIndexNumber(dto.getIndexNumber());
        user.setPassword(dto.getPassword());
        return user;
    }
    public static UserPasswordDto map(User user) {
        UserPasswordDto dto = new UserPasswordDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setIndexNumber(user.getIndexNumber());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
