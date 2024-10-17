package au.com.rainmore.centus.controllers.auth;

import au.com.rainmore.centus.configs.json.JsonUtils;
import au.com.rainmore.centus.domains.books.Book;
import au.com.rainmore.centus.domains.users.Account;
import au.com.rainmore.centus.domains.users.AccountStatus;
import au.com.rainmore.centus.services.core.security.CurrentUser;
import au.com.rainmore.centus.services.core.security.CurrentUserDtoConverter;
import au.com.rainmore.centus.services.core.security.CurrentUserService;
import au.com.rainmore.centus.services.core.security.dto.CurrentUserDto;
import au.com.rainmore.centus.services.core.security.jwt.JwtTokenProvider;
import au.com.rainmore.centus.services.users.AccountService;
import au.com.rainmore.centus.services.users.dto.AccountDto;
import au.com.rainmore.centus.services.users.dto.LoginDto;
import au.com.rainmore.centus.utils.PagingUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerCorsTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CurrentUserDtoConverter currentUserDtoConverter;

    @MockBean
    private CurrentUserService currentUserService;
    @MockBean
    private JwtTokenProvider   jwtTokenProvider;


    @MockBean
    private AccountService accountService;

    @Test
    void test_cors_configuration() throws Exception {
        LoginDto loginDto = new LoginDto(
                "test@test.com",
                "test"
        );

        CurrentUser currentUser = new CurrentUser(
                new Account(),
                Set.of()
        );

        CurrentUserDto currentUserDto = new CurrentUserDto(
                new AccountDto(
                        1L,
                        "test",
                        "test",
                        "test@test.com",
                        AccountStatus.ACTIVE,
                        LocalDateTime.now()
                ),
                "test@test.com",
                true,
                Set.of()
        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

        given(currentUserService.loadUserByLoginDto(loginDto)).willReturn(currentUser);
        given(authenticationManager.authenticate(authentication)).willReturn(authentication);
        willDoNothing().given(accountService).updateLastLoginIn(currentUser.getAccount());
        given(currentUserDtoConverter.convert(currentUser)).willReturn(currentUserDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/auth/login", objectMapper.writeValueAsBytes(loginDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

}
