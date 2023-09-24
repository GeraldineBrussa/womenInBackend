package com.met.metcamp.web.womeninbackend.events.model;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum EventType {
    ANIVERSARIO,
    CLASE_METCAMP,
    ENCUENTRO_METLAB,
    @JsonEnumDefaultValue UNKNOWN,

}
