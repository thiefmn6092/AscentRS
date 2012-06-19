package net.thiefmn6092.ascent.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Encodes outgoing packets into an {@link org.apache.mina.core.buffer.IoBuffer}
 * @author thiefmn6092
 *
 */
public class GameEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession session) throws Exception {

	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		
	}

}
