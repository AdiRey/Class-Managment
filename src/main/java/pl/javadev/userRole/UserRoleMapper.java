package pl.javadev.userRole;

public class UserRoleMapper {
    public static UserRoleDto map(UserRole role) {
        UserRoleDto dto = new UserRoleDto();
        dto.setId(role.getId());
        dto.setRole(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }

    public static UserRole map(UserRoleDto dto) {
        UserRole role = new UserRole();
        role.setId(dto.getId());
        role.setName(dto.getRole());
        role.setDescription(dto.getDescription());
        return role;
    }
}
