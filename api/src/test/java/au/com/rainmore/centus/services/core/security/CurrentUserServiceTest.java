package au.com.rainmore.centus.services.core.security;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.domains.users.Permission;
import au.com.rainmore.centus.domains.users.Role;
import au.com.rainmore.centus.services.users.AccountRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class CurrentUserServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private CurrentUserService currentUserService;

    @BeforeEach
    void setUp() {
        currentUserService = new CurrentUserService(accountRepository);
    }

    @Test
    void test_loadUserByUsername_throw_exception() {
        String email = "test@test.com";
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> currentUserService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Can't find the account by the given username");
    }

    @Test
    void test_loadUserByUsername_return_current_user() {
        String email = "test@test.com";
        Account account = new Account();
        account.setEmail(email);
        account.setFirstname("test");
        account.setLastname("test");
        account.setPassword("test");
        account.setRoles(getRoles());
        account.setPermissions(getPermissions());

        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.of(account));

        UserDetails currentUser = currentUserService.loadUserByUsername(email);

        Set<SimpleGrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"),
                new SimpleGrantedAuthority("PERMISSION_ADMINISTRATOR"),
                new SimpleGrantedAuthority("PERMISSION_USER"),
                new SimpleGrantedAuthority("PERMISSION_TEST1"),
                new SimpleGrantedAuthority("PERMISSION_TEST2"));

        Assertions.assertThat(currentUser)
                .isNotNull()
                .isInstanceOf(UserDetails.class)
                .isInstanceOf(CurrentUser.class)
                .returns(email, BDDAssertions.from(UserDetails::getUsername))
                .returns("test", BDDAssertions.from(UserDetails::getPassword))
                .returns(true, BDDAssertions.from(UserDetails::isAccountNonExpired))
                .returns(true, BDDAssertions.from(UserDetails::isCredentialsNonExpired))
                .returns(account.getStatus().isActive(), BDDAssertions.from(UserDetails::isAccountNonLocked))
                .returns(authorities, BDDAssertions.from(UserDetails::getAuthorities))
        ;
    }

    private Set<Role> getRoles() {
        Role role1 = new Role();
        role1.setName("ROLE_USER");

        Permission permission1 = new Permission();
        permission1.setName("PERMISSION_ADMINISTRATOR");
        role1.getPermissions().add(permission1);

        Role role2 = new Role();
        role2.setName("ROLE_ADMINISTRATOR");

        Permission permission2 = new Permission();
        permission2.setName("PERMISSION_USER");

        role2.getPermissions().add(permission2);

        return Set.of(role1, role2);
    }

    private Set<Permission> getPermissions() {
        Permission permission1 = new Permission();
        permission1.setName("PERMISSION_TEST1");


        Permission permission2 = new Permission();
        permission2.setName("PERMISSION_TEST2");

        return Set.of(permission1, permission2);
    }
}
