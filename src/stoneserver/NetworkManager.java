package stoneserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import stoneserver.network.ClientManager;

public class NetworkManager extends Thread {
	public int port = 25565;
	
	protected ArrayList<ClientManager> clients;
	
	private boolean running = false;
	private ServerSocket sock;
	private MinecraftServer server;
	
	public NetworkManager(MinecraftServer server){ this.server = server; }
	
	public NetworkManager(MinecraftServer server, int port){
		this.server = server;
		this.port = port;
	}
	
	public void run(){
		//TODO: Init Socket
		this.setName("NetworkManager");
		this.clients = new ArrayList<ClientManager>();
		try {
			this.sock = new ServerSocket(port);
			this.server.getLogger().info("Server started on *:"+port+", using protocol version #5");
			
			while(running){
				Socket client = this.sock.accept();
				ClientManager manager = new ClientManager(client, server);
				manager.Start();
				clients.add(manager);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
