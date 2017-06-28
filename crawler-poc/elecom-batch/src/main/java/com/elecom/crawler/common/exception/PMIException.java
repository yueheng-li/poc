package com.elecom.crawler.common.exception;

public class PMIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PMIException() {
		super();
	}

	public PMIException(String message) {
		super(message);
	}

	public PMIException(String message, Throwable cause) {
		super(message, cause);
	}

	public PMIException(Throwable cause) {
		super(cause);
	}

}
