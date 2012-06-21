package net.thiefmn6092.ascent.net.codec;

import net.thiefmn6092.ascent.net.Packet;
import net.thiefmn6092.ascent.net.PacketType;
import net.thiefmn6092.ascent.net.util.ISAACCipher;
import net.thiefmn6092.ascent.util.Constants;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Decodes incoming frames after login.
 * 
 * @author thiefmn6092
 * 
 */
public class GameDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		if(in.remaining() >= 1) {
			ISAACCipher decryption = (ISAACCipher) session.getAttribute("decryption");
			int opcode = in.get() & 0xff;
			opcode = (opcode - decryption.getNextValue()) & 0xff;
			int size = Constants.PACKET_SIZES[opcode];
			if(size == -1) {
				size = in.get() & 0xff;
			} else if(size == -2) {
				size = in.getShort();
			}
			if(in.remaining() >= size) {
				byte[] data = new byte[size];
				in.get(data, 0, size);
				IoBuffer payload = IoBuffer.allocate(size);
				payload.put(data);
				out.write(new Packet(opcode, PacketType.FIXED, payload.flip()));
				return true;
			}
		} else {
			in.rewind();
			return false;
		}
		return false;
		/*
		 * Uncomment the following line for scripting implementation.
		 * 
		 * return ScriptManager.getSingleton().invoke("protocol_decode", session, in, out);
		 * 
		 */
	}

}
