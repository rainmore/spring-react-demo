package au.com.rainmore.centus.controllers.auth;


import au.com.rainmore.centus.configs.json.JsonResponse;
import au.com.rainmore.centus.controllers.BaseRestController;
import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.services.core.security.CurrentUser;
import au.com.rainmore.centus.services.core.security.CurrentUserDtoConverter;
import au.com.rainmore.centus.services.core.security.CurrentUserService;
import au.com.rainmore.centus.services.core.security.dto.CurrentUserDto;
import au.com.rainmore.centus.services.core.security.jwt.JwtTokenProvider;
import au.com.rainmore.centus.services.users.AccountService;
import au.com.rainmore.centus.services.users.dto.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication APIs")
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseRestController {

    private final CurrentUserService      currentUserService;
    private final JwtTokenProvider        jwtTokenProvider;
    private final AuthenticationManager   authenticationManager;
    private final CurrentUserDtoConverter currentUserDtoConverter;
    private final AccountService          accountService;

    @Autowired
    public AuthController(
            CurrentUserService currentUserService,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager,
            CurrentUserDtoConverter currentUserDtoConverter,
            AccountService accountService) {
        this.currentUserService = currentUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.currentUserDtoConverter = currentUserDtoConverter;
        this.accountService = accountService;
    }

    @Operation(summary = "Login",
            description = "This API to login.")
    @ApiResponse(
            responseCode = "200",
            description = "The response allows a valid user to login.",
            content = @Content(schema = @Schema(implementation = CurrentUserDto.class)))
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginDto loginDto) throws EntityNotFoundException {
        CurrentUser currentUser = currentUserService.loadUserByLoginDto(loginDto);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        accountService.updateLastLoginIn(currentUser.getAccount());
        CurrentUserDto userDto = currentUserDtoConverter.convert(currentUser);
        HttpStatus httpStatus = HttpStatus.OK;
        HttpHeaders httpHeaders = jwtTokenProvider.buildHttpHeaders(currentUser);
        JsonResponse<CurrentUserDto> jsonResponse = new JsonResponse<>(userDto, httpStatus.getReasonPhrase());
        return jsonResponse.toResponseEntity(httpHeaders, httpStatus);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleAuthenticationException(UsernameNotFoundException ex, HttpServletResponse response) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        JsonResponse<Object> jsonResponse = new JsonResponse<>(null, ex.getMessage());
        return jsonResponse.toResponseEntity(httpStatus);
    }
}
