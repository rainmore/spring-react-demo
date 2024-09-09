package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.services.users.dto.LoginDto;
import au.com.rainmore.centus.services.users.dto.PasswordDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository     accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Account findActiveOneByEmail(String email) throws EntityNotFoundException {
        return accountRepository.findOneByEmail(email)
                .filter(account -> Boolean.TRUE.equals(account.getStatus().isActive()))
                .orElseThrow(() -> new EntityNotFoundException("Account with email " + email + " not found."));
    }

    public Account findActiveOneByLoginDto(LoginDto loginDto) throws EntityNotFoundException {
        Account account = findActiveOneByEmail(loginDto.username());
        if (!bCryptPasswordEncoder.matches(loginDto.password(), account.getPassword())) {
            throw new EntityNotFoundException("Can't find the account by the given username and password.");
        }

        return account;
    }

    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public void save(Account account, PasswordDto passwordDto) {
        account.setPassword(encodePassword(passwordDto));
        accountRepository.save(account);
    }

    @Transactional
    public void updateLastLoginIn(Account account) {
        account.setLastLoginAt(LocalDateTime.now());
        accountRepository.save(account);
    }

    public String encodePassword(PasswordDto passwordDto) {
        return bCryptPasswordEncoder.encode(passwordDto.newPassword());
    }

}
