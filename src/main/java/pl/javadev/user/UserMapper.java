package pl.javadev.user;

import pl.javadev.userRole.UserRoleMapper;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto entityToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setIndexNumber(user.getIndexNumber());
        dto.setYear(user.getYear());
        dto.setMajor(user.getMajor());
        dto.setRoles(user.getRoles().stream().map(UserRoleMapper::entityToDto).collect(Collectors.toSet()));
        return dto;
    }

    public static User dtoToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setIndexNumber(dto.getIndexNumber());
        user.setYear(dto.getYear());
        user.setMajor(dto.getMajor());
        user.setRoles(dto.getRoles().stream().map(UserRoleMapper::dtoToEntity).collect(Collectors.toSet()));
        return user;
    }

    public static UserDtoReg entityToDtoReg(User user) {
        UserDtoReg dto = new UserDtoReg();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setIndexNumber(user.getIndexNumber());
        dto.setYear(user.getYear());
        dto.setMajor(user.getMajor());
        return dto;
    }

    public static User dtoRegToEntity(UserDtoReg dtoReg) {
        User user = new User();
        user.setId(dtoReg.getId());
        user.setEmail(dtoReg.getEmail());
        user.setPassword(dtoReg.getPassword());
        user.setFirstName(dtoReg.getFirstName());
        user.setLastName(dtoReg.getLastName());
        user.setIndexNumber(dtoReg.getIndexNumber());
        user.setYear(dtoReg.getYear());
        user.setMajor(dtoReg.getMajor());
        return user;
    }

}
