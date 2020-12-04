package org.backend.exception;


public class Exceptions {
    static public class DataNotFoundException extends RuntimeException{

        public DataNotFoundException(String message) {
            super(message);
        }
    }
    static public class AlreadyExistException extends RuntimeException{

        public AlreadyExistException(String message) {
            super(message);
        }
    }
}

