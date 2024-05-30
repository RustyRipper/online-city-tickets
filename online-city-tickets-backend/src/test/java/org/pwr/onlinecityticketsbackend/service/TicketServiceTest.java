package org.pwr.onlinecityticketsbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pwr.onlinecityticketsbackend.config.RequestContext;
import org.pwr.onlinecityticketsbackend.dto.ticket.PurchaseTicketReqDto;
import org.pwr.onlinecityticketsbackend.dto.ticket.TicketDto;
import org.pwr.onlinecityticketsbackend.exception.*;
import org.pwr.onlinecityticketsbackend.mapper.TicketMapper;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.AccountRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketOfferRepository;
import org.pwr.onlinecityticketsbackend.repository.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock private TicketMapper ticketMapper;

    @Mock private TicketRepository ticketRepository;

    @Mock private AccountRepository accountRepository;

    @Mock private TicketOfferRepository ticketOfferRepository;

    @InjectMocks private TicketService ticketService;

    private Passenger passenger;
    private TicketOffer ticketOffer;
    private PurchaseTicketReqDto purchaseTicketReqDto;

    @BeforeEach
    public void setup() throws UnauthorizedUser {
        passenger = new Passenger();
        passenger.setId(1L);
        passenger.setWalletBalancePln(BigDecimal.valueOf(100));
        passenger.setRole(Role.PASSENGER);
        passenger.setEmail("123@wp.pl");

        ticketOffer = new SingleFareOffer();
        ticketOffer.setPricePln(BigDecimal.valueOf(50));

        purchaseTicketReqDto = new PurchaseTicketReqDto();
        purchaseTicketReqDto.setOfferId(1L);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(passenger);
        when(RequestContext.getAccountFromRequest()).thenReturn(passenger);
    }

    @Test
    public void purchaseTicket_success()
            throws TicketInsufficientFunds,
                    AuthenticationInvalidRequest,
                    UnauthorizedUser,
                    TicketOfferNotFound {

        when(ticketOfferRepository.findById(anyLong())).thenReturn(Optional.of(ticketOffer));

        TicketDto ticketDto = ticketService.purchaseTicket(purchaseTicketReqDto);

        verify(accountRepository, times(1)).save(passenger);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void purchaseTicket_insufficientFunds() {
        passenger.setWalletBalancePln(BigDecimal.valueOf(10));

        when(ticketOfferRepository.findById(anyLong())).thenReturn(Optional.of(ticketOffer));

        assertThrows(
                TicketInsufficientFunds.class,
                () -> ticketService.purchaseTicket(purchaseTicketReqDto));
    }

    @Test
    public void purchaseTicket_offerNotFound() {
        when(ticketOfferRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(
                TicketOfferNotFound.class,
                () -> ticketService.purchaseTicket(purchaseTicketReqDto));
    }

    @Test
    public void listTickets_success() throws UnauthorizedUser, AuthenticationInvalidRequest {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());

        when(ticketRepository.findByPassengerId(passenger.getId())).thenReturn(tickets);
        when(ticketMapper.toDto(any(Ticket.class))).thenReturn(new TicketDto());

        List<TicketDto> ticketDtos = ticketService.listTickets();

        assertEquals(2, ticketDtos.size());
        verify(ticketRepository, times(1)).findByPassengerId(passenger.getId());
        verify(ticketMapper, times(2)).toDto(any(Ticket.class));
    }

    @Test
    public void getTicket_success()
            throws UnauthorizedUser, TicketNotFound, AuthenticationInvalidRequest {
        Ticket ticket = new Ticket();
        ticket.setCode("1234567890");
        ticket.setPassenger(passenger);

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));
        when(ticketMapper.toDto(any(Ticket.class))).thenReturn(new TicketDto());

        TicketDto ticketDto = ticketService.getTicket("1234567890");

        assertNotNull(ticketDto);
        verify(ticketRepository, times(1)).findByCode(anyString());
        verify(ticketMapper, times(1)).toDto(any(Ticket.class));
    }

    @Test
    public void getTicket_ticketNotFound() {
        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.empty());

        assertThrows(TicketNotFound.class, () -> ticketService.getTicket("1234567890"));
    }
}
