package stoneserver.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class MCDataOutputStream extends DataOutputStream {

	public MCDataOutputStream(OutputStream arg0) {
		super(arg0);
	}
	
	public void writeString(String string) throws IOException{
		int length = string.length();
		this.writeVarInt(length);
		
		byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
		this.write(bytes);
		
	}
	
	public void writeVarInt(int paramInt) throws IOException {
	    while (true) {
	      if ((paramInt & 0xFFFFFF80) == 0) {
	        this.writeByte(paramInt);
	        return;
	      }
	 
	      this.writeByte(paramInt & 0x7F | 0x80);
	      paramInt >>>= 7;
	    }
	  }

}
