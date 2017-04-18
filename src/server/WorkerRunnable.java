package server;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**

 */
public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
    	long lastRequest = System.currentTimeMillis();
    	boolean ifKeepAlive = false;
    	while(true){
    		try {
            	if(clientSocket.isClosed() || clientSocket.isInputShutdown()
            			|| clientSocket.isOutputShutdown()){
            		break;
            	}
            	long cur = System.currentTimeMillis();
            	if( ( lastRequest + Config.KEEP_ALIVE) < cur ){
            		clientSocket.close();
            		break;
            	}
            	                
                InputStreamReader input = new InputStreamReader(
						clientSocket.getInputStream());
				DataOutputStream output = new DataOutputStream(
						clientSocket.getOutputStream());
				BufferedReader in = new BufferedReader(input);
                
                String request = null;
                if(in.ready())
                	request = in.readLine();
                if(request != null){
                	String[] tockens = request.split(" ");
                	String method = tockens[0];
                	String protocol = tockens[2];
                	
                	String connection = null;
                	String tmp = null;
                	do {
                		tmp = in.readLine();
                		if(tmp.contains("Connection")){
                			connection = tmp;
                		}
                	}while(tmp != null && !tmp.equals(""));
                	
                	if(protocol.equals("HTTP/1.1")){
                		if(connection == null || connection.equals("Connection: keep-alive")){
                			ifKeepAlive = true;
                		}
                		
                	} else if(protocol.equals("HTTP/1.0")) {
                		if(connection.equals("Connection: keep-alive")){
                			ifKeepAlive = true;
                		}
                	}
                	
                	
                		
                	
                	// handle GET
                	long time = System.currentTimeMillis();
                	if(method.equals("GET")){
                		RequestHandler.handleGET(output, serverText + "-" + time, protocol, ifKeepAlive);
                	}
                	
                	if(!ifKeepAlive){
                     	
                        clientSocket.close();
                     	break;
                    }
                	lastRequest = System.currentTimeMillis();
                	System.out.println("Request processed: " + time);
                }
                
               
                
                
                
            } catch (IOException e) {
                //report exception 
                e.printStackTrace();
                if(!clientSocket.isClosed()){
                	try{
                		clientSocket.close();
                	}
                	catch(IOException e2){
                		e2.printStackTrace();
                	}
                }
                break;
            }
    	}
        
    }
}