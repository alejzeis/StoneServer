package stoneserver.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import org.json.simple.*;

import stoneserver.MinecraftServer;
import stoneserver.io.MCDataInputStream;
import stoneserver.io.MCDataOutputStream;
import stoneserver.network.packet.HandshakePacket;
import stoneserver.network.packet.ListPingResponse;
import stoneserver.util.Util;

public class ClientManager extends Thread {
	private MinecraftServer server;
	private boolean running = false;
	private Socket sock;
	
	protected MCDataOutputStream out;
	protected MCDataInputStream in;
	
	public ClientManager(Socket clientSock, MinecraftServer server){
		String addr = clientSock.getInetAddress().toString();
		this.setName("Client-"+addr);
		
		this.sock = clientSock;
		this.server = server;
	}
	
	public void run(){
		try{
			out = new MCDataOutputStream(this.sock.getOutputStream());
			in = new MCDataInputStream(this.sock.getInputStream());
			while(running){
				byte packetLength = (byte) in.readVarInt();
				byte PID = (byte) in.readVarInt(); //Get the packet ID
				//server.getLogger().info("Recived packet ID: "+PID);
				switch(PID){
				
				case 0x00:
					//A Handshake packet
					handleHandshake();
					
					break;
					
				case 0x01: //Ping packet
					long pingID = in.readLong();
					int responseLength = 9;
					Util.writeVarInt(responseLength, out);
					out.writeByte(0x01);
					out.writeLong(pingID);
					
					break;
					
					
				default:
					server.getLogger().info("Unsupported PID recived! "+PID);
				}
				if(running == false){
					break;
				}
			}
		} catch(IOException e){
			//TODO: Auto gen catch block
			e.printStackTrace();
		}
	}
	
	private void handleHandshake(){
		try{
			HandshakePacket hp = new HandshakePacket();
			hp.fromInput(in);
			
			if(hp.nextState == 1){ //Status Ping
				in.readByte(); //Status Ping Request
				ListPingResponse response = new ListPingResponse(getListJson().toJSONString());
				response.toOutput(out);
			}
			
		} catch (Exception e) {
			server.getLogger().error("Exception while handling a HandshakePacket: ");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getListJson(){
		JSONObject versionObj = new JSONObject();
		versionObj.put("name", "StoneServer 1.8");
		versionObj.put("protocol", 5);
		
		JSONObject players = new JSONObject();
		players.put("max", 2);
		players.put("online", 0);
		
		JSONObject serverName = new JSONObject();
		serverName.put("text", "Harro! Dis is a cool server asdfasdf");
		
		JSONObject root = new JSONObject();
		root.put("version", versionObj);
		root.put("players", players);
		root.put("description", serverName);
		
		return root;
	}
	
	public void Start(){
		if(running != true){
			this.running = true;
			this.start();
		}
		else{
			throw new RuntimeException("Tried to start a thread while it was already running.");
		}
	}
	
	public void Stop(){
		if(running != false){
			this.running = false;
		}
		else{
			throw new RuntimeException("Tried to stop a thread when it wasn't running!");
		}
	}


}
