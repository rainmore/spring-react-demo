package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Account;

import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<Account> findOneByEmail(String email);
}
