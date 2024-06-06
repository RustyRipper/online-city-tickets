package org.pwr.onlinecityticketsbackend.service.common;

import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.UpdateCreditCardReqDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCardValidators {
    private final Clock clock;

    public boolean isCreditCardExpired(String expirationDate) {
        var expirationYear = Integer.parseInt(expirationDate.substring(3));
        var expirationMonth = Integer.parseInt(expirationDate.substring(0, 2));

        var now = clock.instant().atZone(clock.getZone());

        var currentYear = now.getYear() % 100;
        var currentMonth = now.getMonthValue();

        return expirationYear < currentYear
                || (expirationYear == currentYear && expirationMonth < currentMonth);
    }

    private static boolean isValidLuhn(String number) {
        int n = number.length();
        int total = 0;
        boolean even = true;
        for (int i = n - 2; i >= 0; i--) {
            int digit = number.charAt(i) - '0';
            if (digit < 0 || digit > 9) {
                return false;
            }
            if (even) {
                digit <<= 1;
            }
            even = !even;
            total += digit > 9 ? digit - 9 : digit;
        }
        int checksum = number.charAt(n - 1) - '0';
        return (total + checksum) % 10 == 0;
    }

    public static boolean isCreditCardValid(SaveCreditCardReqDto saveCreditCardReqDto) {
        var label = saveCreditCardReqDto.getLabel();
        var number = saveCreditCardReqDto.getNumber();
        var name = saveCreditCardReqDto.getHolderName();
        var expiration = saveCreditCardReqDto.getExpirationDate();

        return (label == null || isLabelValid(label))
                && (number != null && isNumberValid(number))
                && (name != null && isHolderNameValid(name))
                && (expiration != null && isExpirationDateValid(expiration))
                && isValidLuhn(number);
    }

    public static boolean isCreditCardUpdateValid(UpdateCreditCardReqDto updateCreditCardReqDto) {
        var label = updateCreditCardReqDto.getLabel();
        var expiration = updateCreditCardReqDto.getExpirationDate();

        return (label == null || isLabelValid(label))
                && (expiration == null || isExpirationDateValid(expiration));
    }

    public static boolean isCvcValid(String cvc) {
        return cvc.length() == 3 && cvc.chars().allMatch(Character::isDigit);
    }

    private static boolean isLabelValid(String label) {
        return label.length() <= 50;
    }

    private static boolean isNumberValid(String number) {
        return number.length() == 16 && number.chars().allMatch(Character::isDigit);
    }

    private static boolean isHolderNameValid(String holderName) {
        return holderName.length() >= 1 && holderName.length() <= 70;
    }

    private static boolean isExpirationDateValid(String expirationDate) {
        return expirationDate.length() == 5 && expirationDate.matches("^(0[1-9]|1[0-2])/[0-9]{2}$");
    }
}
