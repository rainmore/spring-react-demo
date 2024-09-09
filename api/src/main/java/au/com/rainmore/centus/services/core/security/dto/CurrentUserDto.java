package au.com.rainmore.centus.services.core.security.dto;

import au.com.rainmore.centus.services.users.dto.AccountDto;

import java.util.Set;

public record CurrentUserDto(
        AccountDto account,
        String username,
        Boolean enabled,
        Set<String> authorities
) {

}
