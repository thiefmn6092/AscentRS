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
	
	/**
	 * The login server opcode.
	 */
	public static final int OPCODE_LOGIN = 14;
	
	/**
	 * The update server opcode.
	 */
	public static final int OPCODE_UPDATE = 15;

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		/*
		 * Get the session's current login state, if there is none, set it to read opcode.
		 */
		LoginState loginState = (LoginState) session.getAttribute("login_state", LoginState.READ_OPCODE);
		
		/*
		 * Allocate a buffer for writing.
		 */
		IoBuffer buffer = IoBuffer.allocate(16);
		buffer.setAutoExpand(true);
		buffer.setAutoShrink(true);
		
		switch(loginState) {
		case READ_OPCODE:
			if(in.remaining() >= 1) {
				int opcode = in.get() & 0xff;
				switch(opcode) {
				case OPCODE_LOGIN:
					session.setAttribute("login_state", LoginState.EXCHANGE_INFO);
					return true;
				case OPCODE_UPDATE:
					/*
					 * TODO Update server implementation.
					 */
					break;
				default:
					session.close(false);
					return false;
				}
			} else {
				in.rewind();
				return false;
			}
			break;
		case EXCHANGE_INFO:
			/*
			 * TODO Finish login block.
			 */
			break;
		}
		/*
		 * Uncomment the following line for the scripting implementation.
		 * return ScriptManager.getSingleton().invoke("protocol_login_decode", session, in, out);
		 */
		return false;
	}

}
