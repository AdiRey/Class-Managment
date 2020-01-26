package pl.javadev.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByIndexNumber(String indexNumber);
    Optional<User> findByEmail(String email);
    @Query(value = "SELECT max(cast(index_number as unsigned)) FROM first.user", nativeQuery = true)
    int lastIndexNumber();
    Page<User> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}
