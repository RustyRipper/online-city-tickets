package org.pwr.onlinecityticketsbackend.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pwr.onlinecityticketsbackend.exception.handler.RestApiException;
import org.springframework.http.HttpStatus;

public class CardAlreadySavedTest {
    private CardAlreadySaved sut = new CardAlreadySaved();

    @Test
    public void shouldCardAlreadySavedBeCorrect() {
        Assertions.assertThat(sut).isInstanceOf(RestApiException.class);
        Assertions.assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.CONFLICT);
        Assertions.assertThat(sut.getDescription()).isEqualTo("CARD_ALREADY_SAVED");
    }
}
