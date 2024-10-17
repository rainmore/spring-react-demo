package au.com.rainmore.centus.services.users;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.services.users.dto.AccountDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountDtoConverter implements Converter<Account, AccountDto> {

    @Transactional(readOnly = true)
    @Override
    public AccountDto convert(Account source) {
        return new AccountDto(
                source.getId(),
                source.getFirstname(),
                source.getLastname(),
                source.getEmail(),
                source.getStatus(),
                source.getLastLoginAt());
    }
}
