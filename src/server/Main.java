package server;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
    	ThreadPooledServer server = new ThreadPooledServer(8887);
    	System.out.println("Server start...");
    	new Thread(server).start();
    	
    	System.out.println(" Please press ENTRE to stop server.");
    	try {
    	    System.in.read();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	System.out.println("Stopping Server");
    	server.stop();
	}
}
