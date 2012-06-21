package net.thiefmn6092.ascent.net.codec;

import net.thiefmn6092.ascent.net.Packet;
import net.thiefmn6092.ascent.net.PacketType;
import net.thiefmn6092.ascent.net.util.ISAACCipher;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Encodes outgoing packets into an {@link org.apache.mina.core.buffer.IoBuffer}
 * 
 * @author thiefmn6092
 * 
 */
public class GameEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession session) throws Exception {

	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		if(message instanceof Packet) {
			ISAACCipher encryption = (ISAACCipher) session.getAttribute("encryption");
			Packet packet = (Packet) message;
			if(!packet.isHeadless()) {
				out.write(packet.getPayload());
			}
			int size = packet.getSize() + 1;
			if(packet.getType() == PacketType.VARIABLE_BYTE) {
				size += 1;
			} else if(packet.getType() == PacketType.VARIABLE_SHORT) {
				size += 2;
			}
			IoBuffer buffer = IoBuffer.allocate(size);
			buffer.put((byte) (packet.getOpcode() + encryption.getNextValue()));
			if(packet.getType() == PacketType.VARIABLE_BYTE) {
				buffer.put((byte) packet.getSize());
			} else if(packet.getType() == PacketType.VARIABLE_SHORT) {
				buffer.putShort((short) packet.getSize());
			}
			buffer.put(packet.getPayload());
			out.write(buffer.flip());
		} else if(message instanceof IoBuffer) {
			out.write(message);
		}
		/*
		 * Uncomment the following line for scripting implementation.
		 * 
		 * ScriptManager.getSingleton().invoke("protocol_encode", session, message, out);
		 * 
		 */
	}

}
