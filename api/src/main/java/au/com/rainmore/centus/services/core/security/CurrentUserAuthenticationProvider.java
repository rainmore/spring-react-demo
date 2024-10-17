package au.com.rainmore.centus.services.core.security;

import au.com.rainmore.centus.services.users.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserAuthenticationProvider implements AuthenticationProvider {

    private final CurrentUserService currentUserService;

    @Autowired
    public CurrentUserAuthenticationProvider(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        LoginDto loginDto = new LoginDto(name, password);

        final CurrentUser currentUser = currentUserService.loadUserByLoginDto(loginDto);

        return new UsernamePasswordAuthenticationToken(currentUser, authentication.getCredentials(), currentUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
