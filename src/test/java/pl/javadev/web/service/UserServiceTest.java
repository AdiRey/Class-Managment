package pl.javadev.web.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.javadev.user.*;
import pl.javadev.userRole.UserRole;
import pl.javadev.userRole.UserRoleRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    private User user;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        user = new User("marxsd@o2.pl", "qwerty123", "312");
    }

    @After
    public void tearDown() {
        reset(userRepository, roleRepository);
    }

    @Test
    public void save() {
        UserRegistrationDto dto = UserRegistrationMapper.map(user);
        dto.setRepeatedPassword("qwerty123");

        when(roleRepository.findByName("USER_ROLE")).thenReturn(new UserRole("USER_ROLE", "xd"));
        when(passwordEncoder.encode(user.getPassword())).thenReturn("******");

        userService.save(dto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void delete() {
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(user.getPassword())).thenReturn("qwerty123");

        userService.delete(1L, UserDeleteMapper.map(user));
        verify(userRepository).delete(any(User.class));
    }

    @Test
    public void deleteAll() {

        when(userRepository.findAll()).thenReturn(List.of());

        userService.deleteAll();
        verify(userRepository).deleteAll();
    }

    @Test
    public void findAllUsersUsingPaging() {
        when(userRepository.findAllByLastNameContainingIgnoreCase(user.getLastName(), PageRequest.of(0,20,
                Sort.by(new Sort.Order(Sort.Direction.ASC,"x")))))
                .thenReturn(Page.empty());
        when(roleRepository.findByName(user.getLastName())).thenReturn(null);

        userService.findAllUsersUsingPaging(0, "ASC", "xyz");
        verify(userRepository).findAllByLastNameContainingIgnoreCase(user.getLastName(), PageRequest.of(0, 20));
    }

    @Test
    public void findUser() {
    }

    @Test
    public void editUser() {
    }

    @Test
    public void editPassword() {
    }

    @Test
    public void findById() {
    }
}