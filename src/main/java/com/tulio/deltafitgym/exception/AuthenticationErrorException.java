package com.tulio.deltafitgym.exception;

public class AuthenticationErrorException extends RuntimeException{

	private static final long serialVersionUID = -2636519030983556687L;
	
	public AuthenticationErrorException(String message){
		super(message);
	}

}
