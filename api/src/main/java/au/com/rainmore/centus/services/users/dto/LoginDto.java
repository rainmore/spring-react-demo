package au.com.rainmore.centus.services.users.dto;

public record LoginDto(
        String username,
        String password,
        boolean rememberMe) {

    public LoginDto(String username, String password) {
        this(username, password, false);
    }

}
