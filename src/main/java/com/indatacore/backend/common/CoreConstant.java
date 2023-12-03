package com.indatacore.backend.common;

public class CoreConstant {
    private CoreConstant() {
        super();
    }

    public static class Pagination {
        private Pagination() {
            super();
        }

        public static final int DEFAULT_PAGE_NUMBER = 0;
        public static final int DEFAULT_PAGE_SIZE = 20;
    }


    public static class CSV {

        private CSV() {
            super();
        }

        public static final int FIRST_NAME_INDEX = 0;
        public static final int LAST_NAME_INDEX = 1;
        public static final int EMAIL_INDEX = 2;
        public static final int PHONE_NUMBER_INDEX = 3;
    }

    public static class Validation {

        private Validation() {
            super();
        }

        public static final String PHONE_NUMBER_REGEX = "^\\+?\\d*$";
        public static final int MAX_PHONE_NUMBER_SIZE = 20;

        public static final int DEFAULT_STRING_FIELD_SIZE = 20;
        public static final int DEFAULT_EMAIL_FIELD_SIZE = 20;
    }

    public static class Messages {

        private Messages() {
            super();
        }

        public static final String FIRST_NAME_BLANK = "First name cannot be blank";
        public static final String LAST_NAME_BLANK = "Last name cannot be blank";
        public static final String EMAIL_BLANK = "Email cannot be blank";
        public static final String EMAIL_INVALID_FORMAT = "Invalid email format";
        public static final String PHONE_NUMBER_BLANK = "Phone number cannot be blank";
        public static final String PHONE_NUMBER_INVALID_FORMAT = "Invalid phone number format";
        public static final String ELEMENT_ALREADY_EXISTS = "email or phone number already in use";


    }
}
