package org.pwr.onlinecityticketsbackend.service;

import lombok.RequiredArgsConstructor;
import org.pwr.onlinecityticketsbackend.repository.CreditCardInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardInfoService {
    private final CreditCardInfoRepository creditCardInfoRepository;
}
