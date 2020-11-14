package org.backend.exception;


public class Exceptions {
    static public class DataNotFoundException extends RuntimeException{

        public DataNotFoundException(String message) {
            super(message);
        }
    }
    static public class UserAlreadyExistException extends RuntimeException{

        public UserAlreadyExistException(String message) {
            super(message);
        }
    }
}

