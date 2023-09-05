package com.met.metcamp.web.womeninbackend.events.model;

import lombok.*;

//@Data
@AllArgsConstructor
@Getter @Setter
@ToString
@NoArgsConstructor
public class Price {
    private TicketType type;
    private Currency currency;
    private double value;
}
