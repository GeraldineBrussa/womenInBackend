package com.met.metcamp.web.womeninbackend.events.model;


import com.met.metcamp.web.womeninbackend.events.annotations.CurrencyEnum;
import com.met.metcamp.web.womeninbackend.events.annotations.TicketTypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.met.metcamp.web.womeninbackend.events.model.Currency.*;
import static com.met.metcamp.web.womeninbackend.events.model.TicketType.*;

//@Data
@AllArgsConstructor
@Getter @Setter
@ToString
@NoArgsConstructor
public class Price {
    @TicketTypeEnum(anyOf = {REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY,})
    private TicketType type;

    @CurrencyEnum(anyOf = {ARS, CLP, COP, USD,})
    private Currency currency;

    @NotNull(message = "Price value is required")
    @Min(value = 0, message = "Price value must be greater than 0")
    private double value;
}
