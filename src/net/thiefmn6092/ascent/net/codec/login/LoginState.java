package net.thiefmn6092.ascent.net.codec.login;

/**
 * Represents the session's current login stage.
 * @author thiefmn6092
 *
 */
public enum LoginState {
	
	/*
	 * When the opcode must be read from the buffer.
	 */
	READ_OPCODE,
	
	/*
	 * The server reads the derived name hash from the client and then sends a response. 
	 */
	INITIAL_RESPONSE,
	
	/*
	 * Before RSA encryption. The code in the client is deceiving.
	 */
	PRE_RSA,
	
	/*
	 * After RSA encryption.
	 */
	POST_RSA,
	
	/*
	 * When the login block has been decoded. 
	 * The session does NOT have to be authenticated to hold this state.
	 */
	COMPLETE;

}
