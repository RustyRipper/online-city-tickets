# Requirement specification for Online City Tickets

## Milestones

- **M1** - 2024-05-16
- **M2** - 2024-06-06
- **M3** - 2024-06-20

## Dictionary

_The translations are based on the ["Public transport step by step"](https://www.wtp.waw.pl/en/public-transport-step-by-step) guide from WTP._

- **Account** (pl. _konto_) - the generalization of _passenger_ and _inspector_.
- **Code** (pl. _kod biletu_) - the unique identifier of a _purchased_ _ticket_.
- **Duration** (pl. _czas ważności_) - the time from the _validation_ for which a _time-limited ticket_ is valid.
- **Inspection** (pl. _sprawdzenie ważności_) - the process in which an _inspector_ checks whether a _ticket_ owned by a _passenger_ is _valid_.
- **Inspector** (pl. _bileter_) - one of the _account_ types, a user of the app belonging to the organization, who can _inspect_ _tickets_.
- **Interval** (pl. _okres ważności_) - the dates in which a _long-term ticket_ is valid.
- **Passenger** (pl. _pasażer_) - one of the _account_ types, a user of the app external to the organization, who can _purchase_ _tickets_.
- **Payment** (pl. _płatność_) - the process of paying for a _ticket_.
- **Purchase** (pl. _zakup_) - the acquisition of a _ticket_ by a _passenger_, backed by a _payment_.
- **Ticket** (pl. _bilet_) - the virtual confirmation of a _passenger_'s right to travel by public transport, _purchased_ using the system.
- **Ticket kind** (pl. _typ biletu_) - either _standard_ or _reduced_.
  - **Standard ticket** (pl. _bilet normalny_) - a _ticket_, which can be used by everyone.
  - **Reduced ticket** (pl. _bilet ulgowy_) - a _ticket_, which can be used by students, seniors, etc.
- **Ticket offer** (pl. _oferta biletu_) - a combination of _ticket scope_ and _ticket kind_.
- **Ticket scope** (pl. _zakres biletu_) - either _single-fare_, _time-limited_ or _long-term_.
  - **Single-fare ticket** (pl. _bilet jednorazowy_) - a _ticket_, which is _valid_ if it was _validated_ in the same _vehicle_.
  - **Time-limited ticket** (pl. _bilet czasowy_) - a _ticket_, which is _valid_ if its _duration_, counted from the moment of _validation_ did not expire yet.
  - **Long-term ticket** (pl. _bilet okresowy_) - a _ticket_, which is _valid_ if used during its _interval_.
- **Validation** (pl. _skasowanie_) - the process of using a _validator_ to ensure a _single-fare_ or _time-limited ticket_ becomes valid.
- **Validator** (pl. _kasownik_) - a physical machine located in a _vehicle_ which allows the _passenger_ to _validate_ a _ticket_.
- **Validity** (pl. _ważność_) - a _ticket_ is either valid or invalid, only a valid ticket grants a right to travel.
- **Vehicle** (pl. _pojazd_) - a bus or a tram identified by a unique side number, for which a _passenger_ buys _tickets_, and within which _validators_ are mounted.

## Requirements and rules

_The [MoSCoW method](https://en.wikipedia.org/wiki/MoSCoW_method#Prioritization_of_requirements) introduces the following requirement categories: must have, should have, could have, won't have. The requirements were synthesized from: a) project description from the course site, b) project requirements from the course site, c) an exploration of [jakdojade.pl](https://jakdojade.pl). The planned milestone is listed next to the MoSCoW category._

| **ID**   | **Requirement / rule**                                                                        | **Part** | **MoSCoW (MS)** |
| -------- | --------------------------------------------------------------------------------------------- | -------- | --------------- |
| **R001** | The passenger can register an account.                                                        | ALL      | MUST (M1)       |
| **R002** | The passenger and the inspector can log in to their accounts.                                 | ALL      | MUST (M1)       |
| **R003** | The passenger can browse through the ticket offers.                                           | ALL      | MUST (M1)       |
| **R004** | The system stores ticket offers and tickets.                                                  | BE       | MUST (M1)       |
| **R005** | There are three available ticket scopes: single-fare, time-limited and long-term.             | BE       | MUST (M1)       |
| **R006** | There are two available ticket kinds: standard and reduced.                                   | BE       | MUST (M1)       |
| **R007** | Each ticket contains a unique code, allowing for its inspection.                              | BE       | MUST (M1)       |
| **R008** | The passenger can select and purchase any available ticket.                                   | ALL      | MUST (M2)       |
| **R009** | The passenger can view their tickets (active and past) in the "My tickets" view.              | ALL      | MUST (M2)       |
| **R010** | The system exposes a REST API for integration with the validators.                            | BE       | MUST (M2)       |
| **R011** | The inspector can inspect (check validity of) a ticket using its code.                        | ALL      | MUST (M3)       |
| **R012** | A single-fare ticket is valid if it was validated in the same vehicle.                        | BE       | MUST (M3)       |
| **R013** | A time-limited ticket is valid if its duration did not expire yet.                            | BE       | MUST (M3)       |
| **R014** | A long-term ticket is valid if used within its interval.                                      | BE       | MUST (M3)       |
| **R015** | The system offers different payment options.                                                  | BE       | MUST (M2)       |
| **R016** | Credit card is an allowed payment option.                                                     | ALL      | MUST (M2)       |
| **R017** | The system uses GitHub Actions to test using CI/CD.                                           | BE       | MUST (M1)       |
| **R018** | The system is tested using unit/integration tests.                                            | ALL      | MUST            |
| **R019** | Credit card details are validated using the Luhn algorithm.                                   | BE       | SHOULD (M2)     |
| **R020** | The ticket code is displayed in the form of QR code.                                          | FE       | SHOULD (M2)     |
| **R021** | The web app is mobile-first and looks good on a mobile phone.                                 | FE       | SHOULD          |
| **R022** | The system uses Docker Compose to ensure single-command startup.                              | BE       | SHOULD (M1)     |
| **R023** | The system exposes a REST API for inspector registration.                                     | BE       | SHOULD (M1)     |
| **R024** | The inspector can inspect a ticked by scanning the QR code.                                   | FE       | COULD (M3)      |
| **R025** | The BLIK codes are an allowed payment option.                                                 | ALL      | COULD (M3)      |
| **R026** | The app introduces a "wallet" feature, letting you top up a virtual account and pay using it. | ALL      | COULD (M3)      |
| **R027** | The credit card details can be stored for future use.                                         | ALL      | COULD (M2)      |
| **R028** | The passenger can set the preferred ticket kind (standard/reduced) in their settings.         | FE       | COULD (M1)      |
| **R029** | The web app is usable on a bigger screen (e.g. a desktop).                                    | FE       | COULD           |
| **R030** | The web app uses two different color themes.                                                  | FE       | COULD (M1)      |
| **R031** | The system is hosted online using a cloud provider                                            | BE       | COULD (M3)      |
| **R032** | The system is tested using E2E tests.                                                         | ALL      | COULD           |
| **R033** | The payment system mock is held as a separate app to simulate real usage.                     | BE       | COULD (M2)      |
|          | The system is integrated with a real payment system, instead of a mock.                       |          | WON'T           |
|          | The system is integrated with a navigation / timetable system.                                |          | WON'T           |
|          | The system provides a city selection with different offers for different cities.              |          | WON'T           |

Note that requirements **R018**, **R021**, **R029** and **R032** belong to all milestones, since they are the non-functional requirements, which don't have their separate tasks and instead should be upheld during the realization of all the other (functional) requirements.
