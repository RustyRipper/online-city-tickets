package org.pwr.onlinecityticketsbackend.repository;

import java.util.List;
import org.pwr.onlinecityticketsbackend.model.CreditCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardInfoRepository extends JpaRepository<CreditCardInfo, Long> {
    List<CreditCardInfo> findAllByOwnerId(Long id);

    boolean existsByCardNumber(String cardNumber);
}
