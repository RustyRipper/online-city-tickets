package org.pwr.onlinecityticketsbackend.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class InvalidPaymentDataTest {
    private InvalidPaymentData sut = new InvalidPaymentData();

    @Test
    public void exceptionDataShouldBeCorrect() {
        Assertions.assertThat(sut).isInstanceOf(RestApiException.class);
        Assertions.assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(sut.getDescription()).isEqualTo("INVALID_PAYMENT_DATA");
    }
}
