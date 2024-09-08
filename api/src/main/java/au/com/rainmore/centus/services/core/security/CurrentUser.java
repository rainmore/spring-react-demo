package au.com.rainmore.centus.services.core.security;

import au.com.rainmore.centus.domains.users.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class CurrentUser implements UserDetails {

    private Account                   account;
    private Set<SimpleGrantedAuthority> authorities;

    public CurrentUser(Account account, Set<SimpleGrantedAuthority> authorities) {
        this.account = account;
        this.authorities = Set.copyOf(authorities);
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getStatus().isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return account.getStatus().isActive();
    }
}
