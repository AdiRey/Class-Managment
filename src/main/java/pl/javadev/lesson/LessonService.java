package pl.javadev.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.javadev.exception.web.DuplicateIndexException;
import pl.javadev.exception.other.WrongTimeException;
import pl.javadev.user.User;
import pl.javadev.user.UserDto;
import pl.javadev.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private LessonStudentsMapper lessonStudentsMapper;
    private UserRepository userRepository;

    public LessonService(LessonRepository lessonRepository, LessonStudentsMapper lessonStudentsMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonStudentsMapper = lessonStudentsMapper;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LessonDto> findAllLessons() {
        return lessonRepository.findAll().stream().map(LessonMapper::map).collect(Collectors.toList());
    }

    public LessonDto findById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(LessonMapper::map).orElse(null);
    }

    public LessonDto save(LessonDto dto) {
        Lesson savedLesson = lessonRepository.save(LessonMapper.map(dto));
        return LessonMapper.map(savedLesson);
    }

    public LessonDto update(LessonDto dto) {
        Optional<Lesson> foundOne = lessonRepository.findById(dto.getId());
        foundOne.ifPresent(
                u -> {
                    if (!u.getId().equals(dto.getId()))
                        throw new DuplicateIndexException("To nie to id!");
                });
        Lesson savedLesson = lessonRepository.save(LessonMapper.map(dto));
        return LessonMapper.map(savedLesson);
    }

    public LessonDto delete(Long id) {
        LessonDto deletedOne = null;
        Optional<Lesson> foundOne = lessonRepository.findById(id);
        if (foundOne.isPresent()) {
            Lesson lesson = foundOne.get();
            if (lesson.getStart().isBefore(LocalDateTime.now())) {
                deletedOne = LessonMapper.map(lesson);
                lessonRepository.delete(lesson);
            } else {
                throw new WrongTimeException();
            }
        }
        return deletedOne;
    }

    @Transactional
    public LessonDto addUsers(Long id, UserDto userDto) {
        Lesson lesson = null;
        Optional<Lesson> foundOne = lessonRepository.findById(id);
        if (foundOne.isPresent()) {
            lesson = foundOne.get();
            Optional<User> user = userRepository.findById(userDto.getId());
            if (user.isPresent()) {
                User user1 = user.get();
                user1.getLessons().add(lesson);
                lesson.getUsers().add(user1);
            }
        }
        return LessonMapper.map(lesson);
    }

    public LessonStudentsDto getAllStudents(Long id) {
        LessonStudentsDto lessonStudentsDto = null;
        Optional<Lesson> stud = lessonRepository.findById(id);
        if (stud.isPresent()) {
            Lesson lesson = stud.get();
            lessonStudentsDto = lessonStudentsMapper.map(lesson);
        }
        return lessonStudentsDto;
    }
}