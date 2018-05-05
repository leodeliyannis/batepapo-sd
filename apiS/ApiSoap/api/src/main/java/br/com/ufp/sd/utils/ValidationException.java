package br.com.ufp.sd.utils;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1767903387745041325L;

	public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public ValidationException(String message) {
        super(message);
    }
}
