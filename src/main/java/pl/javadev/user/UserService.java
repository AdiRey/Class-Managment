package pl.javadev.user;

import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserDto save(UserRegistrationDto dto);
    UserDto delete(Long id, UserDeleteDto dto);
    List<UserDto> deleteAll();
    Page<UserDto> findAllUsersUsingPaging(int numberOfPage, String sortText, String text);
    UserDto findUser(Long id);
    UserDto editUser(Long id, UserDto dto);
    UserDto editPassword(Long id, UserPasswordDto dto);
    UserDto findById(Long id);
}
