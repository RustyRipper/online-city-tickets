package org.pwr.onlinecityticketsbackend.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

class AuthenticationInvalidRequestTest {
    private AuthenticationInvalidRequest sut = new AuthenticationInvalidRequest();

    @Test
    public void shouldAuthenticationEmailInUseBeCorrect() {
        Assertions.assertThat(sut).isInstanceOf(RestApiException.class);
        Assertions.assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(sut.getDescription()).isEqualTo("INVALID_REQUEST_BODY");
    }
}
