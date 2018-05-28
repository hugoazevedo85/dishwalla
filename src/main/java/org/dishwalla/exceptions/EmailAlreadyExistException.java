package org.dishwalla.exceptions;

public class EmailAlreadyExistException extends RuntimeException{
	
	
	private static final long serialVersionUID = 6946500885449169858L;

	public EmailAlreadyExistException(String msg) {
		super(msg);
	}
	
}
