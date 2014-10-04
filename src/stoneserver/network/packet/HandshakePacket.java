package stoneserver.network.packet;

import java.io.IOException;

import stoneserver.io.MCDataInputStream;
import stoneserver.io.MCDataOutputStream;
import stoneserver.network.ClientManager;
import stoneserver.network.MCDataPacket;

public class HandshakePacket implements MCDataPacket {
	public int protocol;
	public String serverAddress;
	public short serverPort;
	public int nextState;
	
	public void fromInput(MCDataInputStream in){
		try {
			protocol = in.readVarInt();
			serverAddress = in.readString();
			serverPort = (short) in.readUnsignedShort();
			nextState = in.readVarInt();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not decode packet.");
		}
	}
	
	public void toOutput(MCDataOutputStream out){
		throw new RuntimeException("This packet is one way only.");
	}

}
