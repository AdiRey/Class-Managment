package pl.javadev.web.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.javadev.user.*;
import pl.javadev.userRole.UserRole;
import pl.javadev.userRole.UserRoleRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private User user;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(new UserRole("ROLE_USER", "xd"));
        when(passwordEncoder.encode(user.getPassword())).thenReturn("******");
        when(userRepository.lastIndexNumber()).thenReturn(10);
        when(userRepository.save(user)).thenReturn(user);

        userService.save(dto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void delete() {
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);

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
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "lastName"));
        int numberOfPage = 0;
        String sortText = "ASC";
        String text = "";

        when(userRepository.findAllByLastNameContainingIgnoreCase
                (text, PageRequest.of(numberOfPage, 20, sort)))
                .thenReturn(Page.empty());

        userService.findAllUsersUsingPaging(numberOfPage, sortText, text);
        verify(userRepository).findAllByLastNameContainingIgnoreCase(text, PageRequest.of(numberOfPage,
                20, sort));
    }

    @Test
    public void findUser() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.findUser(id);
        verify(userRepository).findById(id);
    }
}