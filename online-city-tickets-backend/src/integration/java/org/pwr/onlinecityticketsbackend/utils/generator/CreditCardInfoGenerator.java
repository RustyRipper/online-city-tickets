package org.pwr.onlinecityticketsbackend.utils.generator;

import net.datafaker.Faker;
import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;

public final class CreditCardInfoGenerator {
    private static final Faker faker = new Faker();

    public static CreditCardInfo generateCreditCardInfo() {
        return CreditCardInfo.builder()
                .label(faker.lorem().word())
                .cardNumber(faker.regexify("^[0-9]{16}$"))
                .expirationDate(faker.regexify("^(0[1-9]|1[0-2])/[0-9]{2}$"))
                .holderName(faker.name().fullName())
                .build();
    }
}
