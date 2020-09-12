package com.modec.fpsomanager.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -4272828699463699190L;

	public BusinessException(final Throwable throwable) {
		super(throwable);
	}

	public BusinessException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public BusinessException(final String message) {
		super(message);
	}

}
