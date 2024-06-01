package org.pwr.onlinecityticketsbackend.service;

import java.time.Clock;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.CreditCardDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.UpdateCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.CardAlreadySaved;
import org.pwr.onlinecityticketsbackend.exception.CardExpired;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.InvalidCard;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.mapper.CreditCardInfoMapper;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.model.Passenger;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardInfoService {
    private final CreditCardInfoRepository creditCardInfoRepository;
    private final CreditCardInfoMapper creditCardInfoMapper;
    private final Clock clock;

    public List<CreditCardDto> getAllCreditCardsForUser(Account account) throws NotPassenger {
        if (!account.isPassenger()) {
            throw new NotPassenger();
        }

        return creditCardInfoRepository.findAllByOwnerId(account.getId()).stream()
                .map(creditCardInfoMapper::toDto)
                .toList();
    }

    public CreditCardDto addCreditCardForUser(
            SaveCreditCardReqDto saveCreditCardReqDto, Account account)
            throws NotPassenger, CardExpired, InvalidCard, CardAlreadySaved {
        if (!account.isPassenger()) {
            throw new NotPassenger();
        }

        if (!isCreditCardValid(saveCreditCardReqDto)) {
            throw new InvalidCard();
        }

        if (!isValidLuhn(saveCreditCardReqDto.getNumber())) {
            throw new InvalidCard();
        }

        if (isCreditCardExpired(saveCreditCardReqDto.getExpirationDate())) {
            throw new CardExpired();
        }

        if (creditCardInfoRepository.existsByCardNumber(saveCreditCardReqDto.getNumber())) {
            throw new CardAlreadySaved();
        }

        var creditCardInfo = creditCardInfoMapper.toEntity(saveCreditCardReqDto);
        creditCardInfo.setOwner((Passenger) account);

        return creditCardInfoMapper.toDto(creditCardInfoRepository.save(creditCardInfo));
    }

    public CreditCardDto getCreditCardByIdForUser(Long id, Account account)
            throws NotPassenger, CardNotFound {
        if (!account.isPassenger()) {
            throw new NotPassenger();
        }

        return creditCardInfoRepository
                .findById(id)
                .filter(c -> c.getOwner().equals(account))
                .map(creditCardInfoMapper::toDto)
                .orElseThrow(CardNotFound::new);
    }

    public CreditCardDto updateCreditCardByIdForUser(
            Long id, UpdateCreditCardReqDto updateCreditCardReqDto, Account account)
            throws NotPassenger, CardExpired, InvalidCard, CardNotFound {
        if (!account.isPassenger()) {
            throw new NotPassenger();
        }

        if (!isCreditCardUpdateValid(updateCreditCardReqDto)) {
            throw new InvalidCard();
        }

        if (updateCreditCardReqDto.getExpirationDate() != null
                && isCreditCardExpired(updateCreditCardReqDto.getExpirationDate())) {
            throw new CardExpired();
        }

        var creditCardInfo =
                creditCardInfoRepository
                        .findById(id)
                        .filter(c -> c.getOwner().equals(account))
                        .orElseThrow(CardNotFound::new);

        if (updateCreditCardReqDto.getLabel() != null) {
            creditCardInfo.setLabel(updateCreditCardReqDto.getLabel());
        }

        if (updateCreditCardReqDto.getExpirationDate() != null) {
            creditCardInfo.setExpirationDate(updateCreditCardReqDto.getExpirationDate());
        }

        return creditCardInfoMapper.toDto(creditCardInfoRepository.save(creditCardInfo));
    }

    public void deleteCreditCardByIdForUser(Long id, Account account)
            throws NotPassenger, CardNotFound {
        if (!account.isPassenger()) {
            throw new NotPassenger();
        }

        var creditCardInfo =
                creditCardInfoRepository
                        .findById(id)
                        .filter(c -> c.getOwner().equals(account))
                        .orElseThrow(CardNotFound::new);

        creditCardInfoRepository.delete(creditCardInfo);
    }

    private boolean isCreditCardExpired(String expirationDate) {
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

    private static boolean isCreditCardValid(SaveCreditCardReqDto saveCreditCardReqDto) {
        var label = saveCreditCardReqDto.getLabel();
        var number = saveCreditCardReqDto.getNumber();
        var name = saveCreditCardReqDto.getHolderName();
        var expiration = saveCreditCardReqDto.getExpirationDate();

        return (label == null || isLabelValid(label))
                && (number != null && isNumberValid(number))
                && (name != null && isHolderNameValid(name))
                && (expiration != null && isExpirationDateValid(expiration));
    }

    private static boolean isCreditCardUpdateValid(UpdateCreditCardReqDto updateCreditCardReqDto) {
        var label = updateCreditCardReqDto.getLabel();
        var expiration = updateCreditCardReqDto.getExpirationDate();

        return (label == null || isLabelValid(label))
                && (expiration == null || isExpirationDateValid(expiration));
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
