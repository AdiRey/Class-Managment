package pl.javadev.user.mapper;

import org.springframework.stereotype.Component;
import pl.javadev.user.User;
import pl.javadev.user.dto.UserDto;
import pl.javadev.userRole.UserRole;
import pl.javadev.userRole.UserRoleRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private UserRoleRepository userRoleRepository;

    public UserMapper(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setIndexNumber(user.getIndexNumber());
        dto.setGrade(user.getGrade());
        dto.setMajor(user.getMajor());
        dto.setRoles(user.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet()));
        return dto;
    }

    public User map(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setIndexNumber(dto.getIndexNumber());
        user.setGrade(dto.getGrade());
        user.setMajor(dto.getMajor());
        Set<UserRole> roles = new HashSet<>();
        for (String role : dto.getRoles()) {
            roles.add(userRoleRepository.findByName(role));
        }
        user.setRoles(roles);
        return user;
    }
}
