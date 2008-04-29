package blms.facade;

import blms.exceptions.BlmsException;

/**
 * This exception is thrown if no user exists with given last name.
 * 
 * @author Dalton Cézane
 * @author Diego Santos
 * @author Rodrigo Rocha
 * 
 */
public class UnknownLastNameException extends BlmsException {

	public UnknownLastNameException(String message) {
		super(message);
	}
}
