package pl.javadev.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.javadev.exception.DuplicateIdxException;
import pl.javadev.exception.WrongTimeException;
import pl.javadev.user.User;
import pl.javadev.user.UserDto;
import pl.javadev.user.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private UserRepository userRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll().stream().map(LessonMapper::entityToDto).collect(Collectors.toList());
    }

    public LessonDto save(LessonDto dto) {
        Lesson savedLesson = lessonRepository.save(LessonMapper.dtoToEntity(dto));
        return LessonMapper.entityToDto(savedLesson);
    }

    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    public LessonDto update(LessonDto dto) {
        Optional<Lesson> foundOne = lessonRepository.findById(dto.getId());
        foundOne.ifPresent(
                u -> {
                    if (!u.getId().equals(dto.getId()))
                        throw new DuplicateIdxException("To nie to id!");
                });
        Lesson savedLesson = lessonRepository.save(LessonMapper.dtoToEntity(dto));
        return LessonMapper.entityToDto(savedLesson);
    }

    public LessonDto delete(Long id) {
        LessonDto deletedOne = null;
        Optional<Lesson> foundOne = lessonRepository.findById(id);
        if (foundOne.isPresent()) {
            Lesson lesson = foundOne.get();
            if (lesson.getEnd().before(Timestamp.from(Instant.now()))) {
                deletedOne = LessonMapper.entityToDto(lesson);
                lessonRepository.delete(lesson);
            } else {
                throw new WrongTimeException();
            }
        }
        return deletedOne;
    }

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
                lessonRepository.save(lesson);
            }
        }
        return LessonMapper.entityToDto(lesson);
    }

    public LessonStudDto getAllStudents(Long id) {
        LessonStudDto lessonStudDto = null;
        Optional<Lesson> stud = lessonRepository.findById(id);
        if (stud.isPresent()) {
            Lesson lesson = stud.get();
            lessonStudDto = LessonMapper.entityToStudDto(lesson);
        }
        return lessonStudDto;
    }
}