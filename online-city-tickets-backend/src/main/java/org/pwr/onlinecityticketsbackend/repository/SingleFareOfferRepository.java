package org.pwr.onlinecityticketsbackend.repository;

import org.pwr.onlinecityticketsbackend.model.SingleFareOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleFareOfferRepository extends JpaRepository<SingleFareOffer, Long> {

}
