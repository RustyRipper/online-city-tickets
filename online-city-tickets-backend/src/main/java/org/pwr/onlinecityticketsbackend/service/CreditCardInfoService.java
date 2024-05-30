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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CreditCardDto getCreditCardByIdForUser(Long id, Account account)
            throws NotPassenger, CardExpired, InvalidCard, CardAlreadySaved {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CreditCardDto addCreditCardForUser(
            SaveCreditCardReqDto saveCreditCardReqDto, Account account)
            throws NotPassenger, CardNotFound {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CreditCardDto updateCreditCardByIdForUser(
            Long id, SaveCreditCardReqDto saveCreditCardReqDto, Account account)
            throws NotPassenger, CardExpired, InvalidCard, CardNotFound {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void deleteCreditCardByIdForUser(Long id, Account account)
            throws NotPassenger, CardNotFound {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
