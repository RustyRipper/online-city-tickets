package org.pwr.onlinecityticketsbackend.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

class TicketInsufficientFundsTest {
    private TicketInsufficientFunds sut = new TicketInsufficientFunds();

    @Test
    public void shouldAccountNotFoundBeCorrect() {
        Assertions.assertThat(sut).isInstanceOf(RestApiException.class);
        Assertions.assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
        Assertions.assertThat(sut.getDescription()).isEqualTo("INSUFFICIENTS_FUNDS");
    }
}
