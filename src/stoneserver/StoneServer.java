package stoneserver;

public class StoneServer {
	
	public static void main(String[] args){
		MinecraftServer s = new MinecraftServer(25565, 2, "StoneServer, Testing!");
		s.Start();
	}

}
