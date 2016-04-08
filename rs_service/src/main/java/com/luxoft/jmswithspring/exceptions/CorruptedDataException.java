package com.luxoft.jmswithspring.exceptions;

public class CorruptedDataException extends Exception {

    private String corruptedLine;

    public CorruptedDataException() {
        super();
    }

    public CorruptedDataException(String msg) {
    super(msg);
}

    public CorruptedDataException(Exception e) {
        super(e);
    }

    public CorruptedDataException(String msg, Throwable t) {
        super(msg,t);
    }

    public CorruptedDataException(String msg, String corruptedLine) {
        this(msg);
        this.corruptedLine = corruptedLine;
    }

}
