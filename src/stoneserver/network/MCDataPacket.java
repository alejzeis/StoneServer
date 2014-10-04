package stoneserver.network;

import java.io.IOException;

import stoneserver.io.MCDataInputStream;
import stoneserver.io.MCDataOutputStream;

public interface MCDataPacket {
	
	void fromInput(MCDataInputStream in) throws IOException;
	void toOutput(MCDataOutputStream out) throws IOException;

}
