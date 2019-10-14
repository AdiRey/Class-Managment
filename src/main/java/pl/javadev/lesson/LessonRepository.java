package pl.javadev.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
