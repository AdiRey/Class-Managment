package pl.javadev.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.javadev.lesson.Lesson;
import pl.javadev.userRole.UserRole;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserEntityTest {
    @Autowired
    private TestEntityManager entityManager;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    private User user;
    private UserRole role;
    private Lesson lesson;

    @Before
    public void setUp() throws Exception {
        user = new User();
        role = new UserRole();
        lesson = new Lesson();
        user.setFirstName("Adrian");
        user.setLastName("Kowal");
        user.setEmail("kadrian12@o2.pl");
        user.setMajor("Informatyka");
        user.setGrade("2016/2017");
        user.setPassword(encoder.encode("mariuszek12"));
        user.setIndexNumber("123232");
        role.setName("ROLE_USER");
        role.setDescription("Student");
        lesson.setTitle("JavaDev");
        lesson.setDescription("Zajecia z javy");
        lesson.setStart(LocalDateTime.now());
        lesson.setEnd(LocalDateTime.now());
    }

    @Test
    public void saveUserWithoutOthers() {
        User savedUser = entityManager.persistAndFlush(user);
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @Transactional
    public void saveUserAndOthers() {
        user.addRole(role);
        user.addLesson(lesson);
        User savedUser = entityManager.persistAndFlush(user);
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
    }

}
