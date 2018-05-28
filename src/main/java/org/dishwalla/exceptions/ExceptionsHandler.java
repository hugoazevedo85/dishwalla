package org.dishwalla.exceptions;

import org.dishwalla.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messages;

	@ExceptionHandler({ EmailAlreadyExistException.class })
	public ResponseEntity<Object> handleEmailAlreadyExist(RuntimeException ex, WebRequest request){
		GenericResponse response = new GenericResponse("EmailAlreadyExists",ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		BindingResult result = ex.getBindingResult();
		GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "ValidationError");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);		
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleInternal(RuntimeException ex, WebRequest request) {
		GenericResponse response = new GenericResponse(messages.getMessage("message.error", null, request.getLocale()),ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
