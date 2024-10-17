package au.com.rainmore.centus.services.core.security;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.services.users.AccountService;
import au.com.rainmore.centus.services.users.dto.LoginDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CurrentUserService implements UserDetailsService {

    private AccountService accountService;

    @Autowired
    public CurrentUserService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountService.findActiveOneByEmail(username);
            return buildCurrentUser(account);
        }
        catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Can't find the account by the given username.");
        }
    }

    @Transactional(readOnly = true)
    public CurrentUser loadUserByLoginDto(LoginDto loginDto) {
        try {
            Account account = accountService.findActiveOneByLoginDto(loginDto);
            return buildCurrentUser(account);
        }
        catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Can't find the account by the given username and password.");
        }
    }

    private CurrentUser buildCurrentUser(Account account) {
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        account.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName())));
        });
        account.getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new CurrentUser(account, grantedAuthorities);
    }

}
