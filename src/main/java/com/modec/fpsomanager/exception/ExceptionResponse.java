package com.modec.fpsomanager.exception;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = -8291676923788841212L;

	private Date timestamp;
	private String message;
	private String details;
	private String trace;

	public ExceptionResponse(//
			final Date timestamp, //
			final String message, //
			final String details, //
			final String trace) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.trace = trace;
	}

	public ExceptionResponse() {
		super();
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(final String details) {
		this.details = details;
	}

	public String getTrace() {
		return this.trace;
	}

	public void setTrace(final String trace) {
		this.trace = trace;
	}

	@Override
	public String toString() {
		return String.format(//
				"ExceptionResponse [timestamp=%s, message=%s, details=%s, trace=%s]", //
				this.timestamp, //
				this.message, //
				this.details, //
				this.trace);
	}

}
