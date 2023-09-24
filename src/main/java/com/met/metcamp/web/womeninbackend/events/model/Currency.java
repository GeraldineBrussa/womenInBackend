package com.met.metcamp.web.womeninbackend.events.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Currency {
    ARS,
    CLP,
    COP,
    USD,
    @JsonEnumDefaultValue UNKNOWN,
}
