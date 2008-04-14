package blms.facade;

/**
 * This exception is thrown if no user exists with given last name.
 * @author Dalton C�zane
 * @author Diego Santos
 * @author Rodrigo Rocha
 *
 */
public class UnknownLastNameException extends Exception {
	
	public UnknownLastNameException (String message) {
		super(message);
	}
}
