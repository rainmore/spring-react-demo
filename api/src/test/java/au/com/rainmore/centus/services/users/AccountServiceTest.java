package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.domains.users.AccountStatus;
import au.com.rainmore.centus.services.users.dto.LoginDto;
import au.com.rainmore.centus.services.users.dto.PasswordDto;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    void setUp() {
        accountService = new AccountService(
                accountRepository,
                bCryptPasswordEncoder
        );
    }

    @Test
    void test_find_active_one_by_email_given_no_existing_email() {
        String email = "test@test.com";
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> accountService.findActiveOneByEmail(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Account with email " + email + " not found.");
    }

    @Test
    void test_find_active_one_by_email_given_a_suspended_account_throw_exception() {
        String email = "test@test.com";
        Account account = new Account();
        account.setEmail(email);
        account.setStatus(AccountStatus.SUSPENDED);
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.of(account));

        Assertions.assertThatThrownBy(() -> accountService.findActiveOneByEmail(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Account with email " + email + " not found.");
    }

    @Test
    void test_find_active_one_by_email_given_a_disabled_account_throw_exception() {
        String email = "test@test.com";
        Account account = new Account();
        account.setEmail(email);
        account.setStatus(AccountStatus.DISABLED);
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.of(account));

        Assertions.assertThatThrownBy(() -> accountService.findActiveOneByEmail(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Account with email " + email + " not found.");
    }

    @Test
    void test_find_active_one_by_email_return_an_active_account() {
        String email = "test@test.com";
        Account account = new Account();
        account.setEmail(email);
        account.setStatus(AccountStatus.ACTIVE);
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.of(account));
        Account result = accountService.findActiveOneByEmail(email);

        Assertions.assertThat(result)
                .returns(email, Account::getEmail)
                .returns(AccountStatus.ACTIVE, Account::getStatus);
    }

    @Test
    void test_find_active_one_by_login_dto_given_invalid_password_throw_exception() {
        String email = "test@test.com";
        LoginDto loginDto = new LoginDto(email, "guest", true);
        String password = "test";
        String invalidPassword = "invalid_password";
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setStatus(AccountStatus.ACTIVE);
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.of(account));
        BDDMockito.given(bCryptPasswordEncoder.encode(anyString())).willReturn(invalidPassword);

        Assertions.assertThatThrownBy(() -> accountService.findActiveOneByLoginDto(loginDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Can't find the account by the given username and password.");
    }

    @Test
    void test_find_active_one_by_login_dto_return_an_active_account() {
        String email = "test@test.com";
        LoginDto loginDto = new LoginDto(email, "guest", true);
        String password = "test";
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setStatus(AccountStatus.ACTIVE);
        BDDMockito.given(accountRepository.findOneByEmail(email)).willReturn(Optional.of(account));
        BDDMockito.given(bCryptPasswordEncoder.encode(any())).willReturn(password);
        Account result = accountService.findActiveOneByLoginDto(loginDto);

        Assertions.assertThat(result)
                .returns(email, Account::getEmail)
                .returns(password, Account::getPassword)
                .returns(AccountStatus.ACTIVE, Account::getStatus);
    }

    @Test
    void test_save_account_with_passwordDto() {
        PasswordDto passwordDto = new PasswordDto("guest");
        String password = "test";
        Account account = new Account();
        BDDMockito.given(bCryptPasswordEncoder.encode(anyString())).willReturn(password);
        BDDMockito.given(accountRepository.save(account)).willReturn(account);

        accountService.save(account, passwordDto);

        Assertions.assertThat(account.getPassword())
                .isNotNull()
                .isEqualTo(password);

    }
}
