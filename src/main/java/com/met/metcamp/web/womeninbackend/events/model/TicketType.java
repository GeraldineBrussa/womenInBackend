package com.met.metcamp.web.womeninbackend.events.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum TicketType {
    REGULAR_FULL_PASS,
    REGULAR_ONE_DAY,
    VIP_FULL_PASS,
    VIP_ONE_DAY,
    @JsonEnumDefaultValue UNKNOWN,
}
