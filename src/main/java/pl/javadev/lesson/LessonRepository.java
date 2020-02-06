package pl.javadev.lesson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long> {
    Page<Lesson> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
}
