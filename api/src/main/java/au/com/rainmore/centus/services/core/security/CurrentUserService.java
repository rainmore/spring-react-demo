package au.com.rainmore.centus.services.core.security;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.services.users.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CurrentUserService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    public CurrentUserService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findOneByEmail(username)
                .map(this::buildCurrentUser)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find the account by the given username"));
    }

    public CurrentUser buildCurrentUser(Account account) {
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        account.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName())));
        });
        account.getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new CurrentUser(account, grantedAuthorities);
    }
}
