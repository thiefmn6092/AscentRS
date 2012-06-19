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
	 * When the information between client in server is exchanged. 
	 */
	EXCHANGE_INFO,
	
	/*
	 * When the login block has been decoded. 
	 * The session does NOT have to be authenticated to hold this state.
	 */
	COMPLETE;

}
