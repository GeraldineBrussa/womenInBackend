package com.met.metcamp.web.womeninbackend.events.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.met.metcamp.web.womeninbackend.events.annotations.EndDateAfterStartDate;
import com.met.metcamp.web.womeninbackend.events.annotations.EventTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

import static com.met.metcamp.web.womeninbackend.events.model.EventType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EndDateAfterStartDate
public class Event {
    @NotNull(message = "ID is required")
    @Positive(message = "ID must be positive")
    private int id;

    @EventTypeEnum(anyOf = {ANIVERSARIO, CLASE_METCAMP, ENCUENTRO_METLAB,})
    private EventType type;

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name is too short")
    private String name;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty(value = "start_date")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty(value = "end_date")
    private LocalDateTime endDate;

    @NotNull(message = "Attendees is required")
    @Min(value = 0, message = "The number of event attendees must be greater than 0")
    @Max(value = 150, message = "The number of attendees to the event must be less than or equal to 150")
    private Integer attendees;

    @NotBlank(message = "Organizer is required")
    @Size(min = 3, message = "Organizer name is too short")
    private String organizer;

    private List<@Valid Price> prices;

    public void update (@Valid Event newEventData){
        this.type = newEventData.getType() != null ? newEventData.getType() : this.type;
        this.name = newEventData.getName() != null ? newEventData.getName() : this.name;
        this.startDate = newEventData.getStartDate() != null ? newEventData.getStartDate() : this.startDate;
        this.endDate = newEventData.getEndDate() != null ? newEventData.getEndDate() : this.endDate;
        this.attendees = newEventData.getAttendees() != null ? newEventData.getAttendees() : this.attendees;
        this.organizer = newEventData.getOrganizer() != null ? newEventData.getOrganizer() : this.organizer;
        this.prices = newEventData.getPrices() != null ? newEventData.getPrices() : this.prices;
    }

}