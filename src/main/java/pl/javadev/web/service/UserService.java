package pl.javadev.web.service;

import org.springframework.data.domain.Page;
import pl.javadev.user.UserDeleteDto;
import pl.javadev.user.UserDto;
import pl.javadev.user.UserPasswordDto;
import pl.javadev.user.UserRegistrationDto;

import java.util.List;

public interface UserService {
    UserDto save(UserRegistrationDto dto);
    Page<UserDto> findAllUsersUsingPaging(int numberOfPage, String sortText, String text);
    UserDto editUser(Long id, UserDto dto);
    UserDto delete(Long id, UserDeleteDto dto);
    List<UserDto> deleteAll();
    UserDto findUser(Long id);
    UserDto editPassword(Long id, UserPasswordDto dto);
}
