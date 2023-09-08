package com.met.metcamp.web.womeninbackend.events.utils;

public class AppConstants {
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


    /*
    * "Mapped JSON to Event List successfully"
    * "Error mapping JSON to Event List: "
    * "Mapped Event to JSON successfully"
    * "Error mapping Event to JSON: "
    * "Mapped Event List to JSON successfully"
    * "Error mapping Event List to JSON: "
    * */
    /*
    *"EventRepository initialized"
    * "Loaded events from file successfully"
    * "Error reading file"
    * "Saved events to file successfully"
    * Error writing file
    * "Event found with ID: "
    * "Event added successfully with ID: "
    * "Event deleted successfully with ID: "
    * "Event updated successfully with ID: "
     * */
}
