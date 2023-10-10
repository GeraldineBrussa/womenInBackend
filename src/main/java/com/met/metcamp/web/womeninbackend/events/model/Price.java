package com.met.metcamp.web.womeninbackend.events.model;


import com.met.metcamp.web.womeninbackend.events.annotations.EnumValue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;


//@Data
@AllArgsConstructor
@Getter @Setter
@ToString
@NoArgsConstructor
public class Price {

    @EnumValue(message = "Invalid ticket type, must be any of: REGULAR_FULL_PASS, REGULAR_ONE_DAY, VIP_FULL_PASS, VIP_ONE_DAY")
    private TicketType type;

    @EnumValue(message = "Invalid currency, must be any of: ARS, CLP, COP, USD")
    private Currency currency;

    @NotNull(message = "Price value is required")
    @DecimalMin(value = "0.00", message = "Price value must be greater than zero", inclusive = false)
    private Double value;
    @Override
    public int hashCode() {
        return Objects.hash(this.getType().name());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Price that)) return false;
        return this.getType().equals(that.getType());
    }
}
