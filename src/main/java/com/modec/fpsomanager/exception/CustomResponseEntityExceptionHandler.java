package com.modec.fpsomanager.exception;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(final Exception ex, final WebRequest request) {

		final ExceptionResponse exceptionResponse = this.getDefaultResponse(ex, request);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(final Exception ex, final WebRequest request) {
		final ExceptionResponse exceptionResponse = this.getDefaultResponse(ex, request);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

	}

	private ExceptionResponse getDefaultResponse(final Exception ex, final WebRequest request) {
		return new ExceptionResponse(//
				new Date(), //
				ex.getMessage(), //
				request.getDescription(false), //
				ExceptionUtils.getStackTrace(ex));
	}

}
