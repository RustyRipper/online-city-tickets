package org.pwr.onlinecityticketsbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.account.AccountDto;
import org.pwr.onlinecityticketsbackend.dto.account.PassengerDto;
import org.pwr.onlinecityticketsbackend.dto.account.UpdateAccountReqDto;
import org.pwr.onlinecityticketsbackend.exception.AuthenticationInvalidRequest;
import org.pwr.onlinecityticketsbackend.exception.UnauthorizedUser;
import org.pwr.onlinecityticketsbackend.mapper.AccountMapper;
import org.pwr.onlinecityticketsbackend.model.Admin;
import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.InspectorRepository;
import org.pwr.onlinecityticketsbackend.repository.PassengerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock private PassengerRepository passengerRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private InspectorRepository inspectorRepository;
    @Mock private AccountMapper accountMapper;

    @InjectMocks private AccountService sut;

    @Test
    void shouldGetAllAccounts() {
        // given
        var account1 = new Passenger();
        var account2 = new Inspector();

        // when
        when(accountRepository.findAll()).thenReturn(List.of(account1, account2));
        var result = sut.getAllAccounts();

        // then
        Assertions.assertThat(result).hasSize(2).containsExactlyInAnyOrder(account1, account2);
    }

    @Test
    void shouldCreatePassenger() {
        // given
        var passenger = new Passenger();
        passenger.setFullName("fullname");
        passenger.setEmail("email");
        passenger.setPhoneNumber("phoneNumber");
        passenger.setPassword("password");

        // when
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);
        var result = sut.createPassenger("email", "fullname", "phoneNumber", "password");

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFullName()).isEqualTo("fullname");
        Assertions.assertThat(result.getEmail()).isEqualTo("email");
        Assertions.assertThat(result.getPhoneNumber()).isEqualTo("phoneNumber");
    }

    @Test
    void shouldCreateInspector() {
        // given
        var inspector = new Inspector();
        inspector.setFullName("fullname");
        inspector.setEmail("email");
        inspector.setPassword("password");

        // when
        when(inspectorRepository.save(any(Inspector.class))).thenReturn(inspector);
        var result = sut.createInspector("email", "fullname", "password");

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getFullName()).isEqualTo("fullname");
        Assertions.assertThat(result.getEmail()).isEqualTo("email");
    }

    @Test
    void shouldGetAccountByEmail() throws UnauthorizedUser {
        // given
        var account = new Passenger();
        account.setEmail("email");

        // when
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
        var result = sut.getAccountByEmail("email");

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo("email");
    }

    @Test
    void shouldThrowAccountNotFoundWhenEmailNotExists() {
        // when
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        ThrowableAssert.ThrowingCallable resultCallable = () -> sut.getAccountByEmail("email");

        // then
        Assertions.assertThatThrownBy(resultCallable).isInstanceOf(UnauthorizedUser.class);
    }

    @Test
    void shouldCheckEmailInUse() {
        // when
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(new Passenger()));
        var result = sut.isEmailInUse("email");

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void shouldGetAccountById() throws UnauthorizedUser {
        // given
        var account = new Passenger();
        account.setId(1L);

        // when
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
        var result = sut.getAccountById(1L);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowAccountNotFoundWhenIdNotExists() {
        // when
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        ThrowableAssert.ThrowingCallable resultCallable = () -> sut.getAccountById(1L);

        // then
        Assertions.assertThatThrownBy(resultCallable).isInstanceOf(UnauthorizedUser.class);
    }

    @Test
    public void testGetCurrentAccountByEmail() throws UnauthorizedUser {
        Passenger passenger = new Passenger();
        passenger.setEmail("test@test.com");

        AccountDto accountDto = new PassengerDto();
        accountDto.setEmail("test@test.com");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(passenger);

        when(accountRepository.findByEmail(passenger.getEmail()))
                .thenReturn(Optional.of(passenger));
        when(accountMapper.toDto(passenger)).thenReturn(accountDto);

        PassengerDto result = (PassengerDto) sut.getCurrentAccountByEmail();

        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    public void testGetCurrentAccountByEmail_ThrowsAccountNotFound() throws UnauthorizedUser {
        Admin admin = new Admin();
        admin.setEmail("admin@test.com");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(admin);

        when(RequestContext.getAccountFromRequest()).thenReturn(admin);

        assertThrows(UnauthorizedUser.class, () -> sut.getCurrentAccountByEmail());
    }

    @Test
    void shouldUpdateAccount() throws AuthenticationInvalidRequest, UnauthorizedUser {
        // given
        UpdateAccountReqDto updateAccountReqDto = new UpdateAccountReqDto();
        updateAccountReqDto.setFullName("newFullName");
        updateAccountReqDto.setPhoneNumber("123123123");

        Passenger passenger = new Passenger();
        passenger.setFullName("oldFullName");
        passenger.setPhoneNumber("876567987");

        when(RequestContext.getAccountFromRequest()).thenReturn(passenger);

        // when
        sut.updateAccount(updateAccountReqDto);

        // then
        assertEquals("newFullName", passenger.getFullName());
        assertEquals("123123123", passenger.getPhoneNumber());
    }

    @Test
    void shouldNotUpdateAccountWhenRoleIsAdmin() throws UnauthorizedUser {
        // given
        UpdateAccountReqDto updateAccountReqDto = new UpdateAccountReqDto();
        Admin admin = new Admin();

        when(RequestContext.getAccountFromRequest()).thenReturn(admin);

        // when
        ThrowableAssert.ThrowingCallable resultCallable =
                () -> sut.updateAccount(updateAccountReqDto);

        // then
        Assertions.assertThatThrownBy(resultCallable)
                .isInstanceOf(AuthenticationInvalidRequest.class);
    }

    @Test
    void shouldNotUpdatePhoneNumberWhenRoleIsInspector() throws UnauthorizedUser {
        // given
        UpdateAccountReqDto updateAccountReqDto = new UpdateAccountReqDto();
        updateAccountReqDto.setPhoneNumber("123456789");

        Inspector inspector = new Inspector();

        when(RequestContext.getAccountFromRequest()).thenReturn(inspector);

        // when
        ThrowableAssert.ThrowingCallable resultCallable =
                () -> sut.updateAccount(updateAccountReqDto);

        // then
        Assertions.assertThatThrownBy(resultCallable)
                .isInstanceOf(AuthenticationInvalidRequest.class);
    }
}
