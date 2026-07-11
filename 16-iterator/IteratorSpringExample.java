package iterator;

/**
 * Spring Boot: Spring Data's Page<T> is an Iterator/Iterable over a slice of results,
 * hiding the underlying paginated query. You iterate content without knowing the SQL.
 */
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

interface CustomerRepo extends JpaRepository<Object, Long> {}

@Service
public class IteratorSpringExample {
    private final CustomerRepo repo;
    public IteratorSpringExample(CustomerRepo repo) { this.repo = repo; }

    public void printFirstPage() {
        Page<Object> page = repo.findAll(PageRequest.of(0, 20));
        // Page<T> is Iterable — traverse without touching the query mechanics.
        for (Object customer : page) System.out.println(customer);
        System.out.println("Total pages: " + page.getTotalPages());
    }
}
