package net.thiefmn6092.ascent.net.codec.login;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Decodes the login block.
 * @author thiefmn6092
 *
 */
public class LoginDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		LoginState loginState = (LoginState) session.getAttribute("login_state", LoginState.READ_OPCODE);
		switch(loginState) {
		
		}
		/*
		 * Uncomment the following line for the scripting implementation.
		 * return ScriptManager.getSingleton().invoke("protocol_login_decode", session, in, out);
		 */
		return false;
	}

}
