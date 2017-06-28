package com.elecom.crawler.common.exception;

public class ExpectedJobExecutionException extends RuntimeException {
    private static final long serialVersionUID = 8838982304219248527L;

    /**
     * Constructs a new instance with a message.
     * 
     * @param msg the exception message.
     * 
     */
    public ExpectedJobExecutionException(String msg) {
        super(msg);
    }
}
