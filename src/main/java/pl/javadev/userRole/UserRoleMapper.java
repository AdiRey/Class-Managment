package pl.javadev.userRole;

public class UserRoleMapper {
    public static UserRoleDto entityToDto(UserRole role) {
        UserRoleDto dto = new UserRoleDto();
        dto.setId(role.getId());
        dto.setRole(role.getRole());
        dto.setDescription(role.getDescription());
        return dto;
    }

    public static UserRole dtoToEntity(UserRoleDto dto) {
        UserRole role = new UserRole();
        role.setId(dto.getId());
        role.setRole(dto.getRole());
        role.setDescription(dto.getDescription());
        return role;
    }
}
