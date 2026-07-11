package unitofwork;

/**
 * Spring Boot: @Transactional + JPA persistence context IS the Unit of Work.
 * Dirty checking tracks changes; one flush at commit writes them atomically.
 */
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnitOfWorkSpringExample {

    // Assume injected JpaRepositories: accountRepo, ledgerRepo.

    @Transactional  // opens ONE unit of work
    public void transfer(String fromId, String toId, long amount) {
        // Account from = accountRepo.findById(fromId).orElseThrow();
        // Account to   = accountRepo.findById(toId).orElseThrow();
        // from.debit(amount);   // no explicit save needed:
        // to.credit(amount);    // dirty checking tracks both changes
        // ledgerRepo.save(new LedgerEntry(fromId, toId, amount));
        // If anything throws, the WHOLE unit rolls back atomically at method exit.
    }
}
