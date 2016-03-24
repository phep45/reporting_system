package com.luxoft.jmswithspring.exceptions;

public class CorruptedDataException extends Exception {

    public CorruptedDataException(String msg) {
    super(msg);
}

    public CorruptedDataException(Exception e) {
        super(e);
    }

    public CorruptedDataException(String msg, Throwable t) {
        super(msg,t);
    }


}
