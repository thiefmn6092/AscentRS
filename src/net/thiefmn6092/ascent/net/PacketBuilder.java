package net.thiefmn6092.ascent.net;

import net.thiefmn6092.ascent.net.util.ProtocolUtils;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * A class used to build Packets.
 * @author thiefmn6092
 *
 */
public class PacketBuilder {
	
	/**
	 * The packet opcode.
	 */
	private final int opcode;
	
	/**
	 * The packet type.
	 */
	private final PacketType type;
	
	/**
	 * The packet payload.
	 */
	private final IoBuffer payload = IoBuffer.allocate(16);
	
	/**
	 * Creates a new PacketBuilder.
	 * @param opcode The packet opcode.
	 * @param type The packet size type.
	 */
	public PacketBuilder(int opcode, PacketType type) {
		this.opcode = opcode;
		this.type = type;
		payload.setAutoExpand(true);
		payload.setAutoShrink(true);
	}
	
	/**
	 * Creates a new fixed-type PacketBuilder.
	 * @param opcode The opcode.
	 */
	public PacketBuilder(int opcode) {
		this(opcode, PacketType.FIXED);
	}
	
	/**
	 * Creates a new headless PacketBuilder.
	 */
	public PacketBuilder() {
		this(-1);
	}
	
	/**
	 * Puts a byte into the payload.
	 * @param value The value.
	 * @return This instance, for method chaining.
	 */
	public PacketBuilder put(int value) { 
		payload.put((byte) value);
		return this;
	}
	
	/**
	 * Puts a short into the payload.
	 * @param value The value.
	 * @return This instance, for method chaining.
	 */
	public PacketBuilder putShort(int value) {
		payload.putShort((short) value);
		return this;
	}
	
	/**
	 * Puts an integer into the payload.
	 * @param value The value.
	 * @return This instance, for method chaining.
	 */
	public PacketBuilder putInt(int value) {
		payload.putInt(value);
		return this;
	}
	
	/**
	 * Puts a long into the payload.
	 * @param value The value.
	 * @return This instance, for method chaining.
	 */
	public PacketBuilder putLong(long value) {
		payload.putLong(value);
		return this;
	}
	
	/**
	 * Puts a string into the payload.
	 * @param value The value.
	 * @return This instance, for method chaining.
	 */
	public PacketBuilder putString(String value) {
		ProtocolUtils.putRS2String(payload, value);
		return this;
	}
	
	/**
	 * Gets a Packet representation of this PacketBuilder.
	 * @return The Packet.
	 */
	public Packet toPacket() {
		return new Packet(opcode, type, payload.flip());
	}

}
