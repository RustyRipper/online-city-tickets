package org.pwr.onlinecityticketsbackend.repository;

import java.util.Optional;

import org.pwr.onlinecityticketsbackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
