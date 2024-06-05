package org.pwr.onlinecityticketsbackend.service;

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
import org.pwr.onlinecityticketsbackend.service.common.CreditCardValidators;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardInfoService {
    private final CreditCardInfoRepository creditCardInfoRepository;
    private final CreditCardInfoMapper creditCardInfoMapper;
    private final CreditCardValidators creditCardValidators;

    public List<CreditCardDto> getAllCreditCardsForUser(Account account) throws NotPassenger {
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        return creditCardInfoRepository.findAllByOwnerId(account.getId()).stream()
                .map(creditCardInfoMapper::toDto)
                .toList();
    }

    public CreditCardDto addCreditCardForUser(
            SaveCreditCardReqDto saveCreditCardReqDto, Account account)
            throws NotPassenger, CardExpired, InvalidCard, CardAlreadySaved {
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        if (!CreditCardValidators.isCreditCardValid(saveCreditCardReqDto)) {
            throw new InvalidCard();
        }

        if (!CreditCardValidators.isValidLuhn(saveCreditCardReqDto.getNumber())) {
            throw new InvalidCard();
        }

        if (creditCardValidators.isCreditCardExpired(saveCreditCardReqDto.getExpirationDate())) {
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
        if (!(account instanceof Passenger)) {
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
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        if (!CreditCardValidators.isCreditCardUpdateValid(updateCreditCardReqDto)) {
            throw new InvalidCard();
        }

        if (updateCreditCardReqDto.getExpirationDate() != null
                && creditCardValidators.isCreditCardExpired(
                        updateCreditCardReqDto.getExpirationDate())) {
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
        if (!(account instanceof Passenger)) {
            throw new NotPassenger();
        }

        var creditCardInfo =
                creditCardInfoRepository
                        .findById(id)
                        .filter(c -> c.getOwner().equals(account))
                        .orElseThrow(CardNotFound::new);

        creditCardInfoRepository.delete(creditCardInfo);
    }
}
