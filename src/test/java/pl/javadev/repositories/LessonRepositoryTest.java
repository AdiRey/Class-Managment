package pl.javadev.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.javadev.lesson.Lesson;
import pl.javadev.lesson.LessonRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;
    Lesson lesson;

    @Before
    public void setUp() throws Exception {
        lesson = new Lesson();
        lesson.setTitle("Javadev");
        lesson.setDescription("Zajecia z javadev");
        lesson.setStart(LocalDateTime.now());
        lesson.setEnd(LocalDateTime.of(2020,1, 25, 18,15,30));
    }

    @After
    public void tearDown() throws Exception {
        lessonRepository.delete(lesson);
    }

    @Test
    public void save() {
        lesson = lessonRepository.save(lesson);
        assertThat(lesson.getTitle()).isEqualTo(lesson.getTitle());
        assertThat(lesson.getDescription()).isEqualTo(lesson.getDescription());
        assertThat(lesson.getStart()).isEqualTo(lesson.getStart());
        assertThat(lesson.getEnd()).isEqualTo(lesson.getEnd());
    }
}