package com.met.metcamp.web.womeninbackend.events.utils;

public class AppConstants {

    public static class eventServiceMessages {
        public static final String SUCCESS_MESSAGE_CREATED = "Event created successfully";
        public static final String SUCCESS_MESSAGE_UPDATED = "Event updated successfully";
        public static final String SUCCESS_MESSAGE_DELETED = "Event deleted successfully";
        public static final String SUCCESS_MESSAGE_FETCHED = "Fetched events successfully";
        public static final String SUCCESS_MESSAGE_FETCHED_LIST_EMPTY = SUCCESS_MESSAGE_FETCHED + ", but the list is empty";
        public  static final String SUCCESS_MESSAGE_FIND_BY_ID = "Event found successfully with ID: ";
        public static final String ERROR_MESSAGE_NOT_FOUND = "Event not found with ID";
        public static final String ERROR_MESSAGE_ALREADY_EXISTS = "Event already exists when creating an event";
        public static final String ERROR_MESSAGE_GENERIC_EXCEPTION = "Unexpected error during event ";
        public static final String ERROR_MESSAGE_REPO_EXCEPTION ="Repository error during event ";
        public static final String ERROR_MESSAGE_VALIDATION_EXCEPTION ="Validation error during event ";
        public static final String ERROR_MESSAGE_CONVERSION_EXCEPTION ="Conversion error during event ";
    }
    public static class mapperUtilsMessages {
        public static final String SUCCESS_MESSAGE_MAPPED_JSON_TO_EVENT_LIST= "Mapped JSON to Event List successfully";
        public static final String SUCCESS_MESSAGE_MAPPED_JSON_TO_EVENT= "Mapped JSON to Event successfully";
        public static final String SUCCESS_MESSAGE_MAPPED_EVENT_LIST_TO_JSON= "Mapped Event List to JSON successfully";
        public static final String SUCCESS_MESSAGE_MAPPED_EVENT_TO_JSON= "Mapped Event to JSON successfully";
        public static final String ERROR_MESSAGE_MAPPED_EVENT_TO_JSON= "Error mapping Event to JSON: ";
        public static final String ERROR_MESSAGE_MAPPED_JSON_TO_EVENT= "Error mapping JSON to Event: ";
        public static final String ERROR_MESSAGE_MAPPED_EVENT_LIST_TO_JSON= "Error mapping Event List to JSON: ";
        public static final String ERROR_MESSAGE_MAPPED_JSON_TO_EVENT_LIST= "Error mapping JSON to Event List: ";

    }

    public static class eventRepositoryMessages {
        public static final String INITIALIZED_EVENT_REPOSITORY = "EventRepository initialized";
        public static final String SUCCESS_MESSAGE_LOADED_EVENTS = "Loaded events from file successfully";
        public static final String SUCCESS_MESSAGE_SAVED_EVENTS = "Saved events to file successfully";
        public static final String SUCCESS_MESSAGE_FOUND_EVENT_BY_ID ="Event found with ID: ";
        public static final String SUCCESS_MESSAGE_ADDED = "Event added successfully with ID: ";
        public static final String SUCCESS_MESSAGE_DELETED = "Event deleted successfully with ID: ";
        public static final String SUCCESS_MESSAGE_UPDATED = "Event updated successfully with ID: ";
        public static final String ERROR_MESSAGE_READING_FILE= "Error reading file";
        public static final String ERROR_MESSAGE_WRITING_FILE= "Error writing file";

    }
    public static class validationServiceMessages{
        public static final String ERROR_MESSAGE_VALIDATION_DATES_START_END_REQUIRED= "Both start date and end date are required";
        public static final String ERROR_MESSAGE_VALIDATION_DATES_START_IN_FUTURE= "Start date must be in the future";
        public static final String ERROR_MESSAGE_VALIDATION_DATES_END_IN_FUTURE= "End date must be in the future";
        public static final String ERROR_MESSAGE_VALIDATION_DATES_START_BEFORE_END= "Start date must be before end " +
                "date";
        public static final String ERROR_MESSAGE_VALIDATION_NAME_REQUIRED= "Name is required";
        public static final String ERROR_MESSAGE_VALIDATION_NAME_LENGTH= "Name is too short";
        public static final String ERROR_MESSAGE_VALIDATION_ID_REQUIRED= "ID must not be zero";
        public static final String ERROR_MESSAGE_VALIDATION_ID_POSITIVE= "ID must be positive";
        public static final String ERROR_MESSAGE_VALIDATION_ORGANIZER_REQUIRED= "Organizer is required";
        public static final String ERROR_MESSAGE_VALIDATION_ORGANIZER_LENGTH= "Organizer name is too short";
        public static final String ERROR_MESSAGE_VALIDATION_ATTENDEES_MINIMUM= "The number of event attendees must be" +
                " greater than 0";
        public static final String ERROR_MESSAGE_VALIDATION_ATTENDEES_MAXIMUM= "The number of attendees to the event " +
                "must be less than or equal to 50";
        public static final String ERROR_MESSAGE_VALIDATION_EVENT_TYPE_REQUIRED= "Event type is required";
        public static final String ERROR_MESSAGE_VALIDATION_EVENT_TYPE_ENUM= "Invalid event type";
        public static final String ERROR_MESSAGE_VALIDATION_TICKET_TYPE_ENUM= "Invalid ticket type";
        public static final String ERROR_MESSAGE_VALIDATION_CURRENCY_ENUM= "Invalid currency";
        public static final String ERROR_MESSAGE_VALIDATION_VALUE_PRICE_MINIMUM= "Price value must be greater than 0";

    }
    public static class eventControllerMessages{
        public static final String ERROR_MESSAGE_GET_ALL_GENERIC_EXCEPTION ="Unexpected error during fetching events: ";
        public static final String ERROR_MESSAGE_GET_BY_ID_NOT_FOUND = "Event not found with ID ";
        public static final String ERROR_MESSAGE_GET_BY_ID_GENERIC_EXCEPTION = eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
                "fetching with ID ";
        public static final String ERROR_MESSAGE_CREATE_ALREADY_EXISTS = "Event already exists when creating an event";
        public static final String ERROR_MESSAGE_CREATE_VALIDATION_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_VALIDATION_EXCEPTION +
                "creation: ";
        public static final String ERROR_MESSAGE_CREATE_CONVERSION_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_CONVERSION_EXCEPTION +
                "creation: ";
        public static final String ERROR_MESSAGE_CREATE_REPO_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_REPO_EXCEPTION + "creation: ";

        public static final String ERROR_MESSAGE_CREATE_GENERIC_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
                "creation: ";
        public static final String ERROR_MESSAGE_UPDATE_ID_NOT_FOUND = "During updating Event not found with ID ";
        public static final String ERROR_MESSAGE_UPDATE_VALIDATION_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_VALIDATION_EXCEPTION +
                        "updating with ID ";
        public static final String ERROR_MESSAGE_UPDATE_CONVERSION_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_CONVERSION_EXCEPTION +
                        "updating with ID ";
        public static final String ERROR_MESSAGE_UPDATE_REPO_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_REPO_EXCEPTION + "updating with ID ";

        public static final String ERROR_MESSAGE_UPDATE_GENERIC_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
                        "updating with ID ";
        public static final String ERROR_MESSAGE_DELETE_ID_NOT_FOUND = "During deleting Event not found with ID ";
        public static final String ERROR_MESSAGE_DELETE_REPO_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_REPO_EXCEPTION + "deleting with ID ";

        public static final String ERROR_MESSAGE_DELETE_GENERIC_EXCEPTION =
                eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
                        "deleting with ID ";
        public static final String ERROR_MESSAGE_HANDLE_CONVERSION_EXCEPTION_BODY ="Malformed Event JSON: ";
        public static final String ERROR_MESSAGE_GENERIC_EXCEPTION_BODY ="Internal Server Error";
    }

}
