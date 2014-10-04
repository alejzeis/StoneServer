package stoneserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import stoneserver.network.ClientManager;
import stoneserver.util.ServerLogger;

public class MinecraftServer {
	public int port;
	public int maxplayers;
	public String name;
	
	protected ArrayList<ClientManager> clients;
	
	private boolean running = false;
	private ServerSocket socket;
	private ServerLogger logger = new ServerLogger();
	
	public ServerLogger getLogger(){
		return logger;
	}
	
	
	public MinecraftServer(int port, int maxplayers, String name){
		this.port = port;
		this.maxplayers = maxplayers;
		this.name = name;
		this.clients = new ArrayList<ClientManager>();
		Thread.currentThread().setName("ServerThread");
	}
	
	private void run(){
		//logger.info("Testing! Harro!")
		logger.info("Starting Minecraft: PC Server on *:"+port);
		try {
			this.socket = new ServerSocket(port);
			logger.info("Server Started!");
			
			while(running){
				Socket client = this.socket.accept();
				ClientManager manager = new ClientManager(client, this);
				manager.Start();
				clients.add(manager);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Start(){
		if(running == false){
			this.running = true;
			this.run();
		}
		else{
			throw new RuntimeException("Tried to start the server when it is already running.");
		}
	}
	
	public void Stop(){
		if(running == true){
			this.running = false;
		}
		else{
			throw new RuntimeException("Tried to stop the server when it is already running");
		}
	}

}
