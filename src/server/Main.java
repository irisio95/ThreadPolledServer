package server;

public class Main {
	public static void main(String[] args) {
    	ThreadPooledServer server = new ThreadPooledServer(8887);
    	System.out.println("Server start...");
    	new Thread(server).start();
    	
    	try {
    	    Thread.sleep(60 * 1000);
    	} catch (InterruptedException e) {
    	    e.printStackTrace();
    	}
    	System.out.println("Stopping Server");
    	server.stop();
	}
}
