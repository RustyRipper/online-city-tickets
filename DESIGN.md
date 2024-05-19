# Design specification for Online City Tickets

## Front end

### UI/UX design

- The app will use (not yet chosen) a component framework.
- The app's UI should be inspired by [jakdojade.pl](https://jakdojade.pl).

## Back end

### Component diagram

TODO

### Domain class diagram

```mermaid
classDiagram
    class Account {
        <<abstract>>
        long id [1] #lcub;id, readonly#rcub;
        String fullName [1]
        String email [1] #lcub;readonly#rcub;
        String hashedPassword [1]
    }

    class Passenger {
        BigDecimal walletBalancePln [1] = BigDecimal.ZERO
        String phoneNumber [0..1]
    }
    Account <|-- Passenger

    class Inspector {

    }
    Account <|-- Inspector

    class CreditCardInfo {
        long id [1] #lcub;id, readonly#rcub;
        String label [0..1]
        String number [1] #lcub;readonly#rcub;
        String holderName [1] #lcub;readonly#rcub;
        String expirationDate [1]
    }
    CreditCardInfo "*" --> "1 owner {readonly}" Passenger

    class Ticket {
        long id [1] #lcub;id, readonly#rcub;
        String code [1] #lcub;readonly#rcub;
        Instant purchaseTime [1] = Instant.now#lpar;#rpar; #lcub;readonly#rcub;
        boolean getIsValid(Instant now, int vehicleSideNumber)
        Instant getValidFromTime()
        Instant getValidUntilTime()
    }
    Ticket "*" --> "1 owner {readonly}" Passenger
    Ticket "*" --> "1 offer {readonly}" TicketOffer
    Ticket "1" --> "0..1 validation" Validation
    
    class TicketKind {
        <<enumeration>>
        Standard
        Reduced
        double getDiscountPercent()
    }

    class Validation {
        long id [1] #lcub;id, readonly#rcub;
        Instant time [1] #lcub;readonly#rcub;
    }
    Validation "*" --> "1 vehicle {readonly}" Vehicle

    class TicketOffer {
        <<abstract>>
        long id [1] #lcub;id, readonly#rcub;
        boolean isActive [1] = true
        String displayNameEn [1]
        String displayNamePl [1]
        TicketKind kind [1] #lcub;readonly#rcub;
        BigDecimal pricePln [1] #lcub;readonly#rcub;
    }

    class SingleFareOffer {
    }
    TicketOffer <|-- SingleFareOffer

    class TimeLimitedOffer {
        Duration duration [1] #lcub;readonly#rcub;
    }
    TicketOffer <|-- TimeLimitedOffer

    class LongTermOffer {
        int intervalInDays [1] #lcub;readonly#rcub;
    }
    TicketOffer <|-- LongTermOffer

    class Vehicle {
        long id [1] #lcub;id, readonly#rcub;
        boolean isActive [1] = true
        String sideNumber [1] #lcub;readonly#rcub;
    }
```

### Database diagram

TODO

### Endpoints

TODO
