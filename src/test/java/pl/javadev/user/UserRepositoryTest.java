package pl.javadev.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setFirstName("Mariusz");
        user.setLastName("Koskas");
        user.setGrade("2012/2013");
        user.setMajor("Technologia");
        user.setEmail("jasne@o2.pl");
        user.setPassword("dasdsadas");
        user.setIndexNumber("123432");
    }

    @Test
    public void saveUserAndFindById() {
        user = userRepository.save(user);
        assertThat(userRepository.findByEmail("jasne@o2.pl")).isEqualTo(Optional.of(user));
    }
}