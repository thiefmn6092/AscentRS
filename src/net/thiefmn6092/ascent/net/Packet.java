package net.thiefmn6092.ascent.net;

import net.thiefmn6092.ascent.net.util.ProtocolUtils;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * A block of data sent across the network.
 * @author thiefmn6092
 *
 */
public class Packet {
	
	/**
	 * The packet opcode.
	 */
	private final int opcode;
	
	/**
	 * The packet size type.
	 */
	private final PacketType type;
	
	/**
	 * The packet payload.
	 */
	private final IoBuffer payload;
	
	/**
	 * Creates a new Packet object.
	 * @param opcode The packet opcode.
	 * @param type The packet type.
	 * @param payload The packet payload.
	 */
	public Packet(int opcode, PacketType type, IoBuffer payload) {
		this.opcode = opcode;
		this.type = type;
		this.payload = payload;
	}
	
	/**
	 * Gets a byte from the payload.
	 * @return The byte.
	 */
	public int get() {
		return payload.get();
	}
	
	/**
	 * Gets an unsigned byte from the payload.
	 * @return The byte.
	 */
	public int getUnsigned() {
		return payload.getUnsigned();
	}
	
	/**
	 * Gets a short from the payload.
	 * @return The short.
	 */
	public int getShort() {
		return payload.getShort();
	}
	
	/**
	 * Gets an unsigned short from the payload.
	 * @return The short.
	 */
	public int getUnsignedShort() {
		return payload.getUnsignedShort();
	}
	
	/**
	 * Gets an integer from the payload.
	 * @return The integer.
	 */
	public int getInt() {
		return payload.getInt();
	}
	
	/**
	 * Gets a long from the payload.
	 * @return The long.
	 */
	public long getLong() {
		return payload.getLong();
	}
	
	/**
	 * Gets an RS2-type (terminated by char 10) string from the buffer.
	 * @return The string.
	 */
	public String getRS2String() {
		return ProtocolUtils.getRS2String(payload);
	}
	
	/**
	 * Checks whether or not the packet is headless (payload only).
	 * @return <code>True</code> if the packet is headless, <code>false</code> if not.
	 */
	public boolean isHeadless() {
		return opcode == -1;
	}
	
	/**
	 * Gets the size of the packet payload.
	 * @return The size.
	 */
	public int getSize() {
		return payload.limit();
	}
	
	/**
	 * Gets the packet opcode.
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}
	
	/**
	 * Gets the packet size type.
	 * @return The type.
	 */
	public PacketType getType() {
		return type;
	}
	
	/**
	 * Gets the packet payload.
	 * @return The payload.
	 */
	public IoBuffer getPayload() {
		return payload;
	}

}
