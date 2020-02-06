package pl.javadev.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
    Page<Teacher> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}