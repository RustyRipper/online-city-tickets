/* tslint:disable */
/* eslint-disable */
export interface InspectTicketInvalidRes {
  reason: 'ticket-not-found' | 'ticket-not-validated' | 'ticket-expired' | 'vehicle-mismatch';
  status: 'invalid';
}
