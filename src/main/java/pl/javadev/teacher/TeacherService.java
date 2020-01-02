package pl.javadev.teacher;

import org.springframework.stereotype.Service;
import pl.javadev.exception.web.DuplicateIndexException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherDto> findAllTeachers() {
        return teacherRepository.findAll().stream().map(TeacherMapper::map).collect(Collectors.toList());
    }

    public TeacherDto findById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return teacher.map(TeacherMapper::map).orElse(null);
    }

    public TeacherDto save(TeacherDto dto) {
        Teacher savedTeacher = teacherRepository.save(TeacherMapper.map(dto));
        return TeacherMapper.map(savedTeacher);
    }

    public TeacherDto update(TeacherDto dto) {
        Optional<Teacher> foundOne = teacherRepository.findById(dto.getId());
        foundOne.ifPresent(
                u -> {
                    if (!u.getId().equals(dto.getId()))
                        throw new DuplicateIndexException("To nie to id!");
                });
        Teacher savedTeacher = teacherRepository.save(TeacherMapper.map(dto));
        return TeacherMapper.map(savedTeacher);
    }

    public TeacherDto delete(Long id) {
        TeacherDto deletedOne = null;
        Optional<Teacher> foundOne = teacherRepository.findById(id);
        if (foundOne.isPresent()) {
            Teacher teacher = foundOne.get();
            deletedOne = TeacherMapper.map(teacher);
            teacherRepository.delete(teacher);
        }
        return deletedOne;
    }
}
