package pl.javadev.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.javadev.exception.web.DuplicateIndexException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService{
    private TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Page<TeacherDto> findAllTeachersUsingPaging(int numberOfPage, String sortText, String text) {
        Sort sort;
        if (sortText.equals("DESC"))
            sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "lastName"));
        else
            sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "lastName"));
        return teacherRepository.findAllByLastNameContainingIgnoreCase
                (text, PageRequest.of(numberOfPage, 20, sort)).map(TeacherMapper::map);
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

    public List<TeacherDto> deleteAll() {
        List<TeacherDto> teachers = new LinkedList<>();
        for (Teacher teacher : teacherRepository.findAll()) {
            teachers.add(TeacherMapper.map(teacher));
        }
        teacherRepository.deleteAll();
        return teachers;
    }
}
