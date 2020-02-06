package pl.javadev.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.javadev.teacher.Teacher;
import pl.javadev.teacher.TeacherRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;
    Teacher teacher;

    @Before
    public void setUp() throws Exception {
        teacher = new Teacher();
        teacher.setFirstName("Mariusz");
        teacher.setLastName("Kos");
        teacher.setDegree("Doctor");
        teacher.setEmail("mariusz.kos@gmail.com");
    }

    @After
    public void tearDown() throws Exception {
        teacherRepository.delete(teacher);
    }
    @Test
    public void save() {
        Teacher savedTeacher = teacherRepository.save(teacher);
        assertThat(savedTeacher.getEmail()).isEqualTo(teacher.getEmail());
        assertThat(savedTeacher.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(savedTeacher.getLastName()).isEqualTo(teacher.getLastName());
        assertThat(savedTeacher.getDegree()).isEqualTo(teacher.getDegree());
    }
}