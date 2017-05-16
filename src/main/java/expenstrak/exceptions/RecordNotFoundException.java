package expenstrak.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Record")
public class RecordNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2434051728130552962L;	
	
	public RecordNotFoundException() { super(); }
	public RecordNotFoundException(String message) { super(message); }
	public RecordNotFoundException(String message, Throwable cause) { super(message, cause); }
	public RecordNotFoundException(Throwable cause) { super(cause); }
	
}
