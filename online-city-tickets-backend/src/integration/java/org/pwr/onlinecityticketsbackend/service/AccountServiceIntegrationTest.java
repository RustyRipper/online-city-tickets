package org.pwr.onlinecityticketsbackend.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.UnauthorizedUser;
import org.pwr.onlinecityticketsbackend.model.Inspector;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.utils.repository.setup.AccountSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class AccountServiceIntegrationTest {
    @Autowired private AccountService accountService;
    @Autowired private AccountSetup accountSetup;

    @Test
    public void shouldReturnAccountWhenAccountExistsInDatabase() throws UnauthorizedUser {
        // given
        Passenger passenger = accountSetup.setupPassenger();

        // when
        Passenger result = (Passenger) accountService.getAccountByEmail(passenger.getEmail());

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(passenger.getId());
        Assertions.assertThat(result.getFullName()).isEqualTo(passenger.getFullName());
        Assertions.assertThat(result.getPhoneNumber()).isEqualTo(passenger.getPhoneNumber());
        Assertions.assertThat(result.getEmail()).isEqualTo(passenger.getEmail());
        Assertions.assertThat(result.getWalletBalancePln())
                .isEqualTo(passenger.getWalletBalancePln());
    }

    @Test
    public void shouldThrowExceptionWhenAccountDoesNotExistInDatabase() {
        // when
        Throwable throwable =
                Assertions.catchThrowable(
                        () -> accountService.getAccountByEmail("nonexistent@example.com"));

        // then
        Assertions.assertThat(throwable).isInstanceOf(UnauthorizedUser.class);
    }

    @Test
    public void shouldCreateInspector() throws UnauthorizedUser {
        // given
        Inspector inspector = accountSetup.setupInspector();

        // when
        Inspector result = (Inspector) accountService.getAccountByEmail(inspector.getEmail());

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo(inspector.getEmail());
        Assertions.assertThat(result.getFullName()).isEqualTo(inspector.getFullName());
    }
}
