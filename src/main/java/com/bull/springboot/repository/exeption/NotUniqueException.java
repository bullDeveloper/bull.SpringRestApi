package com.bull.springboot.repository.exeption;

public class NotUniqueException extends Exception {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotUniqueException(String errorMessage) {  
    super(errorMessage);  
    }  
}  