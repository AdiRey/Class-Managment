package pl.javadev.user;

public class UserDeleteMapper {
    public static User map(UserDeleteDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setIndexNumber(dto.getIndexNumber());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserDeleteDto map(User user) {
        UserDeleteDto dto = new UserDeleteDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setIndexNumber(user.getIndexNumber());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
