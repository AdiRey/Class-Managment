package pl.javadev.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.javadev.user.User;
import pl.javadev.user.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setFirstName("Adrian");
        user.setLastName("Kowal");
        user.setMajor("Informatyka");
        user.setGrade("2016/2017");
        user.setPassword(encoder.encode("mariuszek12"));
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(user);
    }

    @Test
    public void saveUserAndFindByEmail() {
        user.setEmail("kadrian102@o2.pl");
        user.setIndexNumber("123234");
        user = userRepository.save(user);
        assertThat(userRepository.findByEmail("kadrian102@o2.pl")).isEqualTo(Optional.of(user));
    }

    @Test
    public void save() {
        user.setEmail("kadrian101@o2.pl");
        user.setIndexNumber("123233");
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
    }
}