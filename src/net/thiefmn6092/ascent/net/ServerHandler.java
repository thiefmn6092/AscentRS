package net.thiefmn6092.ascent.net;

import net.thiefmn6092.ascent.Engine;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The MINA server handler.
 * 
 * @author thiefmn6092
 * 
 */
public class ServerHandler extends IoHandlerAdapter {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	@Override
	public void exceptionCaught(IoSession session, final Throwable cause)
			throws Exception {
		Engine.getSingleton().submitLogic(new Runnable() {
			@Override
			public void run() {
				logger.error("Exception caught!", cause.getCause());
			}
		});
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message instanceof Packet) {
			final Packet packet = (Packet) message;
			Engine.getSingleton().submitLogic(new Runnable() {
				@Override
				public void run() {
					
				}
			});
		}
	}
	
	

}
