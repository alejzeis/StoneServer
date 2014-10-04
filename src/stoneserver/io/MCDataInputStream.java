package stoneserver.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class MCDataInputStream extends DataInputStream {

	public MCDataInputStream(InputStream arg0) {
		super(arg0);
	}
	
	
	public String readString() throws IOException{
		String string = null;
		int length = this.readVarInt();
		byte[] bytes = new byte[length];
		this.read(bytes);
		
		string = new String(bytes, Charset.forName("UTF-8"));
		
		return string;
	}
	
	public int readVarInt() throws IOException {
	    int i = 0;
	    int j = 0;
	    while (true) {
	      int k = this.readByte();
	 
	      i |= (k & 0x7F) << j++ * 7;
	 
	      if (j > 5) throw new RuntimeException("VarInt too big");
	 
	      if ((k & 0x80) != 128) break;
	    }
	 
	    return i;
	  }

}
