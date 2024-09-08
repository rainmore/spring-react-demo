package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.services.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

}
