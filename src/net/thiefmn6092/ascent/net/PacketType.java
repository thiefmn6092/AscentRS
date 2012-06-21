package net.thiefmn6092.ascent.net;

/**
 * Represents a type of packet.
 * @author thiefmn6092
 *
 */
public enum PacketType {
	
	/*
	 * The packet size is fixed and known.
	 */
	FIXED,
	
	/*
	 * The packet size is designated by a single byte.
	 */
	VARIABLE_BYTE,
	
	/*
	 * The packet size is designated by a word.
	 */
	VARIABLE_SHORT

}
