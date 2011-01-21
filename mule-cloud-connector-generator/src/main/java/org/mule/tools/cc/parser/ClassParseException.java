package org.mule.tools.cc.parser;

public class ClassParseException extends Exception {
    public ClassParseException(String message) {
        super(message);
    }

    public ClassParseException(String message, Throwable e) {
        super(message, e);
    }
}
