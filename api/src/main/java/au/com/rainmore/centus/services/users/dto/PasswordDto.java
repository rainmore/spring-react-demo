package au.com.rainmore.centus.services.users.dto;

import java.util.Objects;

public record PasswordDto(
        String newPassword,
        String newPasswordConfirm
) {

    public PasswordDto {
        Objects.requireNonNull(newPassword);
    }

    public PasswordDto(String newPassword) {
        this(newPassword, newPassword);
    }
}
