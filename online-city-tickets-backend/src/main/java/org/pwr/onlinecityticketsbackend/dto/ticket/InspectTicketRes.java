package org.pwr.onlinecityticketsbackend.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectTicketRes {
    public static InspectTicketRes valid() {
        return new InspectTicketRes("valid", "");
    }

    public static InspectTicketRes invalid(String reason) {
        return new InspectTicketRes("invalid", reason);
    }

    private String status;
    private String reason;
}
