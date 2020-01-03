package pl.javadev.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.exception.other.WrongTimeException;
import pl.javadev.teacher.Teacher;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.teacher.TeacherRepository;
import pl.javadev.user.User;
import pl.javadev.user.UserRepository;
import pl.javadev.user.UserDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService{
    private LessonRepository lessonRepository;
    private UserRepository userRepository;
    private TeacherRepository teacherRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Page<LessonDto> findAllLessonsUsingPaging(int numberOfPage, String sortText, String text) {
        Sort sort;
        if (sortText.equals("DESC"))
            sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "title"));
        else
            sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "title"));
        return lessonRepository.findAllByTitleContainingIgnoreCase
                (text, PageRequest.of(numberOfPage, 20, sort)).map(LessonMapper::map);
    }

    @Transactional
    public LessonDto addUsers(Long id, UserDto userDto) {
        try {
            Optional<Lesson> foundLesson = lessonRepository.findById(id);
            Lesson lesson = foundLesson.get();
            Optional<User> foundUser = userRepository.findById(userDto.getId());
            lesson.addUser(foundUser.get());
            return LessonMapper.map(lesson);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    @Transactional
    public LessonDto addTeacher(Long id, TeacherDto teacherDto) {
        try {
            Optional<Lesson> foundLesson = lessonRepository.findById(id);
            Lesson lesson = foundLesson.get();
            Optional<Teacher> foundTeacher = teacherRepository.findById(teacherDto.getId());
            lesson.setTeacher(foundTeacher.get());
            return LessonMapper.map(lesson);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public LessonDto findLesson(Long id) {
        try {
            Optional<Lesson> foundLesson = lessonRepository.findById(id);
            return LessonMapper.map(foundLesson.get());
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public LessonDto save(LessonRegistrationDto dto) {
        Lesson savedLesson = lessonRepository.save(LessonRegistrationMapper.map(dto));
        return LessonMapper.map(savedLesson);
    }

    public LessonDto findById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(LessonMapper::map).orElse(null);
    }

    @Transactional
    public LessonDto editLesson(Long id, LessonRegistrationDto dto) {
        try {
            if (!id.equals(dto.getId()))
                throw new ConflictIdException();
            Optional<Lesson> foundOne = lessonRepository.findById(dto.getId());
            Lesson lesson = foundOne.get();
            lesson.setTitle(dto.getTitle());
            lesson.setDescription(dto.getDescription());
            lesson.setStart(dto.getStart());
            lesson.setEnd(dto.getEnd());
            return LessonMapper.map(lesson);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public LessonDto delete(Long id) {
        try {
            Optional<Lesson> foundLesson = lessonRepository.findById(id);
            Lesson lesson = foundLesson.get();
            if (lesson.getStart().isBefore(LocalDateTime.now())) {
                LessonDto deletedOne = LessonMapper.map(lesson);
                lessonRepository.delete(lesson);
                return deletedOne;
            } else {
                throw new WrongTimeException();
            }
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public List<LessonDto> deleteAll() {
        List<LessonDto> lessons = new LinkedList<>();
        for (Lesson lesson : lessonRepository.findAll()) {
            lessons.add(LessonMapper.map(lesson));
        }
        userRepository.deleteAll();
        return lessons;
    }
}