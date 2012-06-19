package net.thiefmn6092.ascent.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.thiefmn6092.ascent.net.codec.GameCodecFactory;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.firewall.ConnectionThrottleFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initializes the MINA {@link org.apache.mina.core.service.IoAcceptor}.
 * @author thiefmn6092
 *
 */
public class Server {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private final Logger logger = LoggerFactory.getLogger(Server.class);
	
	/**
	 * The socket acceptor.
	 */
	private IoAcceptor acceptor = new NioSocketAcceptor();
	
	/**
	 * The server handler.
	 */
	public static final ServerHandler SERVER_HANDLER = new ServerHandler();
	
	/**
	 * The connection throttle filter.
	 */
	public static final IoFilter THROTTLE_FILTER = new ConnectionThrottleFilter(10000);
	
	/**
	 * The MINA codec factory.
	 */
	public static final IoFilter CODEC_FACTORY = new ProtocolCodecFilter(new GameCodecFactory());
	
	/**
	 * Creates a new Server instance.
	 */
	public Server() {
		logger.info("Configuring socket acceptor...");
		acceptor.getFilterChain().addFirst("throttle_filter", THROTTLE_FILTER);
		acceptor.getFilterChain().addLast("codec_factory", CODEC_FACTORY);
		acceptor.setHandler(SERVER_HANDLER);
	}
	
	/**
	 * Binds the socket acceptor to a port.
	 * @param port The port to bind the acceptor to.
	 * @return This instance, for method chaining.
	 * @throws IOException If the port has already been bound.
	 */
	public Server bind(int port) throws IOException {
		logger.info("Binding socket acceptor to port: " + port + "...");
		acceptor.bind(new InetSocketAddress(43594));
		return this;
	}

}
