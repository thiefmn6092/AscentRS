package net.thiefmn6092.ascent.net.util;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * A utility class for the RuneScape 2 protocol.
 * 
 * @author thiefmn6092
 * 
 */
public class ProtocolUtils {

	/**
	 * Gets an RS2-type string. Terminated by byte 10.
	 * 
	 * @param in
	 *            The buffer to read the string from.
	 * @return The string read.
	 */
	public static final String getRS2String(IoBuffer in) {
		StringBuffer buffer = new StringBuffer();
		char c;
		while ((c = (char) in.get()) != 10) {
			buffer.append(c);
		}
		return buffer.toString();
	}

}
