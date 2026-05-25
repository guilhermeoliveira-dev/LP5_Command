package org.example.exception;

import org.example.log.ILogger;
import org.example.log.LogType;

public class IllegalActionException extends RuntimeException {
    public IllegalActionException(ILogger log, String message) {
        super(message);
        log.log(LogType.EXCEPTION, message);
    }
}
