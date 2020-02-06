package pl.javadev.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.exception.web.DuplicateIndexException;
import pl.javadev.teacher.Teacher;
import pl.javadev.teacher.TeacherDto;
import pl.javadev.teacher.TeacherMapper;
import pl.javadev.teacher.TeacherRepository;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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

    public TeacherDto findTeacher(Long id) {
        try {
            Teacher teacher = teacherRepository.findById(id).get();
            return TeacherMapper.map(teacher);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public TeacherDto save(TeacherDto dto) {
        Teacher savedTeacher = teacherRepository.save(TeacherMapper.map(dto));
        return TeacherMapper.map(savedTeacher);
    }

    @Transactional
    public TeacherDto edit(Long id, TeacherDto dto) {
        try {
            if (!id.equals(dto.getId()))
                throw new ConflictIdException();
            Teacher teacher = teacherRepository.findById(dto.getId()).get();
            teacher.setFirstName(dto.getFirstName());
            teacher.setLastName(dto.getLastName());
            teacher.setEmail(dto.getEmail());
            teacher.setDegree(dto.getDegree());
            return TeacherMapper.map(teacher);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public TeacherDto delete(Long id) {
        try {
            Teacher teacher = teacherRepository.findById(id).get();
            TeacherDto deletedTeacher = TeacherMapper.map(teacher);
            teacherRepository.delete(teacher);
            return deletedTeacher;
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
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
