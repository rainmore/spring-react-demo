package au.com.rainmore.centus.services.users.dto;

import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.domains.users.AccountStatus;

import java.time.LocalDateTime;

public record AccountDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        AccountStatus status,
        LocalDateTime lastLoginAt
) {

}
