package com.modec.fpsomanager.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8340323980687498330L;

	public EntityNotFoundException(final Throwable throwable) {
		super(throwable);
	}

	public EntityNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public EntityNotFoundException(final String message) {
		super(message);
	}

}
