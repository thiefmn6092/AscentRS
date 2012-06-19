package net.thiefmn6092.ascent.net.codec;

import net.thiefmn6092.ascent.net.codec.login.LoginDecoder;
import net.thiefmn6092.ascent.net.codec.login.LoginState;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * The MINA codec factory.
 * @author thiefmn6092
 *
 */
public class GameCodecFactory implements ProtocolCodecFactory {
	
	/**
	 * The login decoder.
	 */
	public static final ProtocolDecoder LOGIN_DECODER = new LoginDecoder();
	
	/**
	 * The game decoder.
	 */
	public static final ProtocolDecoder GAME_DECODER = new GameDecoder();
	
	/**
	 * The game encoder.
	 */
	public static final ProtocolEncoder GAME_ENCODER = new GameEncoder();

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		boolean loginComplete = (LoginState) session.getAttribute("login_state") == LoginState.COMPLETE;
		return loginComplete ? GAME_DECODER : LOGIN_DECODER;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return GAME_ENCODER;
	}

}
