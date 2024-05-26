package org.pwr.onlinecityticketsbackend.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class AccountNotFoundTest {
    private AccountNotFound sut = new AccountNotFound();

    @Test
    public void shouldAccountNotFoundBeCorrect() {
        Assertions.assertThat(sut).isInstanceOf(RestApiException.class);
        Assertions.assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(sut.getDescription()).isEqualTo("ACCOUNT_NOT_FOUND");
    }
}
