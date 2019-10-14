package pl.javadev.user;

class UserMapper {
    static UserDto entityToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setIndexNumber(user.getIndexNumber());
        dto.setYear(user.getYear());
        dto.setMajor(user.getMajor());
        return dto;
    }

    static UserDtoReg entityToDtoReg(User user) {
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

    static User dtoRegToEntity(UserDtoReg dtoReg) {
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
