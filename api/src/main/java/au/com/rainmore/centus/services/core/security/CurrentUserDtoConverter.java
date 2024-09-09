package au.com.rainmore.centus.services.core.security;

import au.com.rainmore.centus.services.core.security.dto.CurrentUserDto;
import au.com.rainmore.centus.services.users.AccountDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class CurrentUserDtoConverter implements Converter<CurrentUser, CurrentUserDto> {

    private final AccountDtoConverter accountDtoConverter;

    @Autowired
    public CurrentUserDtoConverter(AccountDtoConverter accountDtoConverter) {
        this.accountDtoConverter = accountDtoConverter;
    }

    @Transactional(readOnly = true)
    @Override
    public CurrentUserDto convert(CurrentUser source) {
        return new CurrentUserDto(
                accountDtoConverter.convert(source.getAccount()),
                source.getUsername(),
                source.isEnabled(),
                source.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet())
        );
    }
}
