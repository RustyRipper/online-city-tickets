package org.pwr.onlinecityticketsbackend.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.CreditCardDto;
import org.pwr.onlinecityticketsbackend.dto.creditCardInfo.SaveCreditCardReqDto;
import org.pwr.onlinecityticketsbackend.exception.CardAlreadySaved;
import org.pwr.onlinecityticketsbackend.exception.CardExpired;
import org.pwr.onlinecityticketsbackend.exception.CardNotFound;
import org.pwr.onlinecityticketsbackend.exception.InvalidCard;
import org.pwr.onlinecityticketsbackend.exception.NotPassenger;
import org.pwr.onlinecityticketsbackend.mapper.CreditCardInfoMapper;
import org.pwr.onlinecityticketsbackend.model.Account;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardInfoService {
    private final CreditCardInfoRepository creditCardInfoRepository;
    private final CreditCardInfoMapper creditCardInfoMapper;

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

        throw new UnsupportedOperationException("Not implemented yet");
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
            Long id, SaveCreditCardReqDto saveCreditCardReqDto, Account account)
            throws NotPassenger, CardExpired, InvalidCard, CardNotFound {
        if (!account.isPassenger()) {
            throw new NotPassenger();
        }

        throw new UnsupportedOperationException("Not implemented yet");
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
}
