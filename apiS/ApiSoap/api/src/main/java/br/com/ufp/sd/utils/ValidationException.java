package br.com.ufp.sd.utils;

public class ValidationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6948373611343960917L;
	
	public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
	public ValidationException(String message) {
        super(message);
    }
}
