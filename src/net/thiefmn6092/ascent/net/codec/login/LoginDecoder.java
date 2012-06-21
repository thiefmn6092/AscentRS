package net.thiefmn6092.ascent.net.codec.login;

import net.thiefmn6092.ascent.net.util.ISAACCipher;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.thiefmn6092.ascent.net.util.ProtocolUtils.getRS2String;

/**
 * Decodes the login block.
 * @author thiefmn6092
 *
 */
public class LoginDecoder extends CumulativeProtocolDecoder {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoginDecoder.class);
	
	/**
	 * The login server opcode.
	 */
	public static final int OPCODE_LOGIN = 14;
	
	/**
	 * The update server opcode.
	 */
	public static final int OPCODE_UPDATE = 15;
	
	/**
	 * The initial connection opcode.
	 */
	public static final int OPCODE_CONNECT = 16;
	
	/**
	 * The relogin connection opcode.
	 */
	public static final int OPCODE_RECONNECT = 18;

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
				logger.info("opcode: " + opcode);
				switch(opcode) {
				case OPCODE_LOGIN:
					session.setAttribute("login_state", LoginState.INITIAL_RESPONSE);
					return true;
				case OPCODE_UPDATE:
					/*
					 * TODO Update server implementation.
					 */
					break;
				default:
					session.close(false);
					break;
				}
			} else {
				in.rewind();
				return false;
			}
			break;
		case INITIAL_RESPONSE:
			if(in.remaining() >= 1) {
				/*
				 * Read the name hash. This is derived in the client from your username:
				 * 
				 * long l = TextClass.longForName(s);
				 * int i = (int)(l >> 16 & 31L);
				 * 
             	* And is supposedly used for login server selection.
             	*/
				@SuppressWarnings("unused")
				int nameHash = in.get();
				
				/*
				 * The client then reads 8 ignored bytes.
				 * 
				 * for(int j = 0; j < 8; j++)
				 * 		socketStream.read();
				 * 
				 * These are used to maintain a connection with the server.
				 */
				for(int i = 0; i < 8; i++) {
					buffer.put((byte) 0);
				}
			
				/*
				 * Then the client reads the server response.
				 * It is expected to be zero in this case.
				 * 
            	* int k = socketStream.read();
            	* int i1 = k;
            	* if(k == 0)
            	* 
            	*/
				buffer.put((byte) 0);
				
				/*
				 * The client then generates the client half of the ISAAC seed and reads the other.
				 * 
				 * aLong1215 = inStream.readQWord();
	             * int ai[] = new int[4];
	             * ai[0] = (int)(Math.random() * 99999999D);
	             * ai[1] = (int)(Math.random() * 99999999D);
	             * ai[2] = (int)(aLong1215 >> 32);
	             * ai[3] = (int)aLong1215;
				 */
				long seed = (long) (Math.random() * Long.MAX_VALUE);
				buffer.putLong(seed);
				
				session.write(buffer.flip());
				
				session.setAttribute("login_state", LoginState.PRE_RSA);
				session.setAttribute("server_seed", seed);
				return true;
			} else {
				in.rewind();
				return false;
			}
		case PRE_RSA:
			if(in.remaining() >= 1) {
				/*
				 * The client then sends the login opcode.
				 * 
				 * 16 - initial connection.
				 * 18 - reconnection.
				 * 
				 * if(flag)
                 * 		aStream_847.writeWordBigEndian(18);
                 * else
                 * 		aStream_847.writeWordBigEndian(16);
                 * 
				 */
				int opcode = in.get() & 0xff;
				switch(opcode) {
				case OPCODE_CONNECT:
				case OPCODE_RECONNECT:
					if(in.remaining() >= 1) {
						/*
						 * The client then sends the precrypted block size:
						 * 
						 * aStream_847.writeWordBigEndian(stream.currentOffset + 36 + 1 + 1 + 2);
						 * 
						 */
						int size = in.get() & 0xff;
						if(in.remaining() >= size) {
							/*
							 * Then the client writes the byte 255.
							 * 
							 * aStream_847.writeWordBigEndian(255);
							 * 
							 * Most likely to ensure the login block's integrity.
							 */
							int magicId = in.get() & 0xff;
							if(magicId != 255) {
								session.close(false);
								in.rewind();
								return false;
							}
							
							/*
							 * The client then writes the short 317 (client version).
							 * 
							 * aStream_847.writeWord(317);
							 */
							int version = in.getShort();
							if(version != 317) {
								session.close(false);
								in.rewind();
								return false;
							}
							
							/*
							 * The client then writes the lowMem flag.
							 * 
							 * aStream_847.writeWordBigEndian(lowMem ? 1 : 0);
							 */
							@SuppressWarnings("unused")
							boolean lowMem = in.get() == 1;
							
							/*
							 * Then the client sends 9 "expected CRCs".
							 * 
							 * for(int l1 = 0; l1 < 9; l1++)
                    		 * 		aStream_847.writeDWord(expectedCRCs[l1]);
                    		 * 
                    		 * This has something to do with the connectServer method in the client.
                    		 * 
							 */
							for(int i = 0; i < 9; i++) {
								in.getInt();
							}
							
							session.setAttribute("login_state", LoginState.POST_RSA);
							return true;
						} else {
							in.rewind();
							return false;
						}
					} else {
						in.rewind();
						return false;
					}
				}
			} else {
				in.rewind();
				return false;
			}
			break;
		case POST_RSA:
			// RSA has been disabled in the majority of clients.
			if(in.remaining() >= 1) {
				/*
				 * The client writes the encrypted size through the doKeys method in the Stream class.
				 * 
				 * writeWordBigEndian(abyte1.length);
        		 * writeBytes(abyte1, abyte1.length, 0);
        		 * 
				 */
				int size = in.get() & 0xff;
				
				if(in.remaining() >= size) {
					/*
					 * The client writes byte 10.
					 * 
					 * stream.writeWordBigEndian(10);
					 * 
					 */
					int opcode = in.get() & 0xff;
					if(opcode != 10) {
						session.close(false);
						in.rewind();
						return false;
					}
					
					/*
					 * The client then writes the full ISAAC seed.
					 * 
					 * stream.writeDWord(ai[0]);
					 * stream.writeDWord(ai[1]);
					 * stream.writeDWord(ai[2]);
					 * stream.writeDWord(ai[3]);
					 * 
				 	 */
					int[] seed = new int[4];
					for(int i = 0; i < 4; i++) {
						seed[i] = in.getInt();
					}
					
					long reportedServerSeed = ((long) ((seed[2] & 0xffffffffL)) << 32) + ((long) (seed[3] & 0xffffffffL));					
					if(reportedServerSeed != (long) session.getAttribute("server_seed")) {
						session.close(false);
						in.rewind();
						return false;
					}
					
					/*
					 * Then the client writes the client uid.
					 * This is calculated by the following:
					 * 
					 * (int) (Math.random() * 99999999D)
				 	 * 
				 	 * stream.writeDWord(signlink.uid);
				 	 * 
				 	 */
					@SuppressWarnings("unused")
					int uid = in.getInt();
					
					/*
					 * The client then writes the player's username and password.
					 * 
					 * stream.writeString(s);
					 * stream.writeString(s1);
					 * 
					 */
					String username = getRS2String(in);
					String password = getRS2String(in);
					
					/*
					 * Now we seed the ISAAC ciphers as does the client.
					 * 
					 * stream.encryption = new ISAACRandomGen(ai);
					 * for(int j2 = 0; j2 < 4; j2++)
					 * 		ai[j2] += 50;
					 * encryption = new ISAACRandomGen(ai);
					 */
					ISAACCipher decryption = new ISAACCipher(seed);
					for(int i = 0; i < 4; i++) {
						seed[i] += 50;
					}
					ISAACCipher encryption = new ISAACCipher(seed);
					
					/*
					 * TODO Authentication.
					 */
					buffer.put((byte) 2);
					buffer.put((byte) 0);
					buffer.put((byte) 0);
					
					session.write(buffer.flip());
					
					session.setAttribute("login_state", LoginState.COMPLETE);
					session.setAttribute("decryption", decryption);
					session.setAttribute("encryption", encryption);
					return true;
				} else {
					in.rewind();
					return false;
				}
			} else {
				in.rewind();
				return false;
			}
		}
		/*
		 * Uncomment the following line for the scripting implementation.
		 * return ScriptManager.getSingleton().invoke("protocol_login_decode", session, in, out);
		 */
		return false;
	}

}
