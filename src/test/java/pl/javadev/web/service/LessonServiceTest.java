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
import pl.javadev.lesson.Lesson;
import pl.javadev.lesson.LessonRegistrationDto;
import pl.javadev.lesson.LessonRegistrationMapper;
import pl.javadev.lesson.LessonRepository;
import pl.javadev.teacher.Teacher;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.teacher.TeacherMapper;
import pl.javadev.teacher.TeacherRepository;
import pl.javadev.user.User;
import pl.javadev.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class LessonServiceTest {
    @InjectMocks
    private LessonServiceImpl lessonService;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TeacherRepository teacherRepository;

    private Lesson lesson;

    @Before
    public void setUp() {
        lesson = new Lesson("Javadev", "Java language programming",
                LocalDateTime.of(2020,1,27,21,10),
                LocalDateTime.of(2020, 1, 27, 23, 10));
    }

    @After
    public void tearDown() {
        reset(lessonRepository, userRepository, teacherRepository);
    }

    @Test
    public void findAllLessonsUsingPaging() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "title"));
        int numberOfPage = 0;
        String sortText = "ASC";
        String text = "";

        when(lessonRepository.findAllByTitleContainingIgnoreCase
                (text, PageRequest.of(numberOfPage, 20, sort)))
                .thenReturn(Page.empty());

        lessonService.findAllLessonsUsingPaging(numberOfPage, sortText, text);
        verify(lessonRepository).findAllByTitleContainingIgnoreCase(text, PageRequest.of(numberOfPage,
                20, sort));
    }

    @Test
    public void addUsers() {
        Long id = 1L, userId = 2L;
        User user = new User("marxsd@o2.pl", "qwerty123", "312");

        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        lessonService.addUsers(id, userId);
        verify(lessonRepository).findById(id);
        verify(userRepository).findById(userId);
    }

    @Test
    public void addTeacher() {
        Long id = 1L;
        Teacher teacher = new Teacher("Mariusz", "Kos", "engineer", "basxsd@o2.pl");
        TeacherDto teacherDto = TeacherMapper.map(teacher);

        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(teacherRepository.findById(teacherDto.getId())).thenReturn(Optional.of(teacher));

        lessonService.addTeacher(id, teacherDto);
        verify(lessonRepository).findById(id);
        verify(teacherRepository).findById(teacherDto.getId());
    }

    @Test
    public void save() {
        lesson.setId(1L);
        LessonRegistrationDto dto = LessonRegistrationMapper.map(lesson);
        when(lessonRepository.save(LessonRegistrationMapper.map(dto))).thenReturn(lesson);

        lessonService.save(dto);
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    public void delete() {
        Long id = 1L;
        lesson.setId(id);
        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));

        lessonService.delete(id);
        verify(lessonRepository).delete(lesson);
    }

    @Test
    public void deleteAll() {
        when(lessonRepository.findAll()).thenReturn(List.of(lesson));

        lessonService.deleteAll();
        verify(lessonRepository, times(1)).delete(any(Lesson.class));
    }
}