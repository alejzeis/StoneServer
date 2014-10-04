package stoneserver.network.packet;

import java.io.IOException;

import stoneserver.io.MCDataInputStream;
import stoneserver.io.MCDataOutputStream;
import stoneserver.network.MCDataPacket;

public class ListPingResponse implements MCDataPacket {
	public static final byte PID = 0x00;
	public String JSONData;
	
	public ListPingResponse(String JSONData){
		this.JSONData = JSONData;
	}
	
	public void fromInput(MCDataInputStream in){ throw new RuntimeException("Packet is one way only."); }
	
	public void toOutput(MCDataOutputStream out) throws IOException{
		out.writeByte(PID);
		out.writeString(JSONData);
	}

}
