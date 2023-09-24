package com.met.metcamp.web.womeninbackend.events.utils;

public class AppConstants {

    public static class eventServiceMessages {
        public static final String ERROR_MESSAGE_GENERIC_EXCEPTION = "Unexpected error during event ";
        public static final String ERROR_MESSAGE_REPO_EXCEPTION ="Repository error during event ";
        public static final String ERROR_MESSAGE_VALIDATION_EXCEPTION ="Validation error during event ";
        public static final String ERROR_MESSAGE_CONVERSION_EXCEPTION ="Conversion error during event ";
    }


    public static class eventControllerMessages{
        public static final String ERROR_MESSAGE_GET_ALL_GENERIC_EXCEPTION ="Unexpected error during fetching events:" +
                " %s";
        public static final String ERROR_MESSAGE_GET_BY_ID_NOT_FOUND = "Event not found with ID %s: %s";
        //public static final String ERROR_MESSAGE_GET_BY_ID_GENERIC_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
            //    "fetching with ID %s: %s";
        public static final String ERROR_MESSAGE_CREATE_ALREADY_EXISTS = "Event already exists when creating" +
                " an event: %s";
        //public static final String ERROR_MESSAGE_CREATE_VALIDATION_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_VALIDATION_EXCEPTION +
            //    "creation: %s";
        //public static final String ERROR_MESSAGE_CREATE_CONVERSION_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_CONVERSION_EXCEPTION +
            //    "creation: %s";
        //public static final String ERROR_MESSAGE_CREATE_REPO_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_REPO_EXCEPTION + "creation: %s";

        //public static final String ERROR_MESSAGE_CREATE_GENERIC_EXCEPTION =
        //        eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
        //        "creation: %s";
        public static final String ERROR_MESSAGE_UPDATE_ID_NOT_FOUND = "During updating Event not found with ID %s: %s";
        //public static final String ERROR_MESSAGE_UPDATE_VALIDATION_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_VALIDATION_EXCEPTION +
          //              "updating with ID %s: %s";
        //public static final String ERROR_MESSAGE_UPDATE_CONVERSION_EXCEPTION =
        //        eventServiceMessages.ERROR_MESSAGE_CONVERSION_EXCEPTION +
        //                "updating with ID %s: %s";
        //public static final String ERROR_MESSAGE_UPDATE_REPO_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_REPO_EXCEPTION + "updating with ID %s: %s";

        //public static final String ERROR_MESSAGE_UPDATE_GENERIC_EXCEPTION =
          //      eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
          //              "updating with ID %s: %s";
        public static final String ERROR_MESSAGE_DELETE_ID_NOT_FOUND = "During deleting Event not found with ID %s: %s";
        //public static final String ERROR_MESSAGE_DELETE_REPO_EXCEPTION =
        //        eventServiceMessages.ERROR_MESSAGE_REPO_EXCEPTION + "deleting with ID %s: %s";
        public static final String SUCCESS_MESSAGE_DELETED = "Event with ID %s successfully deleted";
        //public static final String ERROR_MESSAGE_DELETE_GENERIC_EXCEPTION =
        //        eventServiceMessages.ERROR_MESSAGE_GENERIC_EXCEPTION +
        //                "deleting with ID %s: %s";
        public static final String ERROR_MESSAGE_HANDLE_CONVERSION_EXCEPTION_BODY ="Malformed Event JSON: %s";
        public static final String ERROR_MESSAGE_GENERIC_EXCEPTION_BODY ="Internal Server Error: %s";
    }

}
