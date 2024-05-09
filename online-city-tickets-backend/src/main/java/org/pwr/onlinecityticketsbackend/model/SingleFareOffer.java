package org.pwr.onlinecityticketsbackend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class SingleFareOffer extends TicketOffer {
}
