package org.pwr.onlinecityticketsbackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pwr.onlinecityticketsbackend.dto.ticket.*;
import org.pwr.onlinecityticketsbackend.exception.*;
import org.pwr.onlinecityticketsbackend.mapper.TicketMapper;
import org.pwr.onlinecityticketsbackend.model.*;
import org.pwr.onlinecityticketsbackend.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TicketServiceTest {

    @Mock private TicketMapper ticketMapper;

    @Mock private TicketRepository ticketRepository;

    @Mock private AccountRepository accountRepository;

    @Mock private TicketOfferRepository ticketOfferRepository;

    @Mock private VehicleRepository vehicleRepository;

    @Mock private ValidationRepository validationRepository;

    @Mock private Clock clock;

    @InjectMocks private TicketService ticketService;

    private Passenger passenger;
    private TicketOffer ticketOffer;
    private PurchaseTicketReqDto purchaseTicketReqDto;

    @BeforeEach
    public void setup() throws UnauthorizedUser {
        passenger = new Passenger();
        passenger.setId(1L);
        passenger.setWalletBalancePln(BigDecimal.valueOf(100));
        passenger.setEmail("123@wp.pl");

        ticketOffer = new SingleFareOffer();
        ticketOffer.setPricePln(BigDecimal.valueOf(50));

        purchaseTicketReqDto = new PurchaseTicketReqDto();
        purchaseTicketReqDto.setOfferId(1L);
        var authentication = mock(Authentication.class);
        var securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(passenger);
    }

    @Test
    public void purchaseTicket_success()
            throws TicketInsufficientFunds,
                    AuthenticationInvalidRequest,
                    UnauthorizedUser,
                    TicketOfferNotFound {

        when(ticketOfferRepository.findById(anyLong())).thenReturn(Optional.of(ticketOffer));

        ticketService.purchaseTicket(purchaseTicketReqDto, passenger);

        verify(accountRepository, times(1)).save(passenger);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void purchaseTicket_insufficientFunds() {
        passenger.setWalletBalancePln(BigDecimal.valueOf(10));

        when(ticketOfferRepository.findById(anyLong())).thenReturn(Optional.of(ticketOffer));

        assertThrows(
                TicketInsufficientFunds.class,
                () -> ticketService.purchaseTicket(purchaseTicketReqDto, passenger));
    }

    @Test
    public void purchaseTicket_offerNotFound() {
        when(ticketOfferRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(
                TicketOfferNotFound.class,
                () -> ticketService.purchaseTicket(purchaseTicketReqDto, passenger));
    }

    @Test
    public void listTickets_success() throws UnauthorizedUser, AuthenticationInvalidRequest {
        var tickets = new ArrayList<Ticket>();
        tickets.add(new Ticket());
        tickets.add(new Ticket());

        when(ticketRepository.findByPassengerId(passenger.getId())).thenReturn(tickets);
        when(ticketMapper.toDto(any(Ticket.class))).thenReturn(new TicketDto());

        var ticketDtos = ticketService.listTickets(passenger);

        assertEquals(2, ticketDtos.size());
        verify(ticketRepository, times(1)).findByPassengerId(passenger.getId());
        verify(ticketMapper, times(2)).toDto(any(Ticket.class));
    }

    @Test
    public void getTicket_success()
            throws UnauthorizedUser, TicketNotFound, AuthenticationInvalidRequest {
        var ticket = new Ticket();
        ticket.setCode("1234567890");
        ticket.setPassenger(passenger);

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));
        when(ticketMapper.toDto(any(Ticket.class))).thenReturn(new TicketDto());

        TicketDto ticketDto = ticketService.getTicket("1234567890", passenger);

        assertNotNull(ticketDto);
        verify(ticketRepository, times(1)).findByCode(anyString());
        verify(ticketMapper, times(1)).toDto(any(Ticket.class));
    }

    @Test
    public void getTicket_ticketNotFound() {
        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.empty());

        assertThrows(TicketNotFound.class, () -> ticketService.getTicket("1234567890", passenger));
    }

    @Test
    public void validateTicket_ticketNotFound() {
        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.empty());

        assertThrows(
                TicketNotFound.class,
                () -> ticketService.validateTicket("1234567890", new ValidateTicketReq()));
    }

    @Test
    public void validateTicket_ticketAlreadyValidated() {
        var ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());
        ticket.setValidation(new Validation());

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));

        assertThrows(
                TicketAlreadyValidated.class,
                () -> ticketService.validateTicket("1234567890", new ValidateTicketReq()));
    }

    @Test
    public void validateTicket_vehicleNotFound() {
        var ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));
        when(vehicleRepository.findBySideNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFound.class,
                () -> ticketService.validateTicket("1234567890", new ValidateTicketReq()));
    }

    @Test
    public void validateTicket_success()
            throws TicketNotFound, TicketAlreadyValidated, VehicleNotFound {
        var ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());

        var vehicle = new Vehicle();
        vehicle.setSideNumber("123");
        var validation = new Validation();
        validation.setVehicle(vehicle);

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));
        when(vehicleRepository.findBySideNumber(anyString())).thenReturn(Optional.of(vehicle));
        when(validationRepository.save(any(Validation.class))).thenReturn(validation);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toDto(any(Ticket.class))).thenReturn(new TicketDto());

        var validateTicketReq = new ValidateTicketReq();
        validateTicketReq.setVehicleSideNumber("123");
        var result = ticketService.validateTicket("1234567890", validateTicketReq);

        assertNotNull(result);
        verify(validationRepository, times(1)).save(any(Validation.class));
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void inspectTicket_unauthorizedUser() throws UnauthorizedUser {
        var account = new Passenger();

        assertThrows(
                AuthenticationInvalidRequest.class,
                () -> ticketService.inspectTicket("1234567890", new InspectTicketReq(), account));
    }

    @Test
    public void inspectTicket_ticketNotFound()
            throws UnauthorizedUser, VehicleNotFound, TicketNotFound, AuthenticationInvalidRequest {
        var account = new Inspector();
        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.empty());
        when(vehicleRepository.findBySideNumber(anyString()))
                .thenReturn(Optional.of(new Vehicle()));

        var request = new InspectTicketReq();
        request.setVehicleSideNumber("123");
        var result = ticketService.inspectTicket("1234567890", request, account);

        assertEquals("invalid", result.getStatus());
        assertEquals("ticket-not-found", result.getReason());
    }

    @Test
    public void inspectTicket_vehicleNotFound() throws UnauthorizedUser {
        var account = new Inspector();
        var ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));
        when(vehicleRepository.findBySideNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFound.class,
                () -> ticketService.inspectTicket("1234567890", new InspectTicketReq(), account));
    }

    @Test
    public void inspectTicket_success()
            throws TicketNotFound, VehicleNotFound, UnauthorizedUser, AuthenticationInvalidRequest {
        var account = new Inspector();
        var ticket = new Ticket();
        ticket.setOffer(new SingleFareOffer());
        var vehicle = new Vehicle();

        when(ticketRepository.findByCode(anyString())).thenReturn(Optional.of(ticket));
        when(vehicleRepository.findBySideNumber(anyString())).thenReturn(Optional.of(vehicle));

        var request = new InspectTicketReq();
        request.setVehicleSideNumber("123");
        var result = ticketService.inspectTicket("1234567890", request, account);

        assertNotNull(result);
    }
}
