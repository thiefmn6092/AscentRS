package net.thiefmn6092.ascent.net.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Decodes incoming frames after login.
 * @author thiefmn6092
 *
 */
public class GameDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		/*
		 * Uncomment the following line for scripting implementation.
		 * return ScriptManager.getSingleton().invoke("protocol_decode", session, in, out);
		 */
		return false;
	}

}
