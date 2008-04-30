package blms.exceptions;

public class BlmsException extends Exception {
	private static final long serialVersionUID = 120633416572705794L;
	/**
	 * Throws the "message" in some cases that exceptions need to be treated with specific messages. 
	 * @param message the message to be thrown.
	 */
	public BlmsException(String message) {
		super(message);
	}
}
