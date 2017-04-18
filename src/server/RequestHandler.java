package server;

import java.io.DataOutputStream;
import java.io.IOException;

public class RequestHandler {
	public static void handleGET(DataOutputStream output, String serverText,
			String protocol, boolean ifKeepAlive) throws IOException{
		
    	StringBuffer header = new StringBuffer();
    	
    	String content = "WorkerRunnable: " +
    	    serverText;
    	
        
    	header.append(protocol + " 200 OK\r\n");
    	header.append("Content-Length: " + content.length() + "\r\n");
        if(ifKeepAlive){
        	header.append("Connection: keep-alive\r\n");
        } else{
        	header.append("Connection: closed\r\n");
        }
        
        header.append("\r\n");

        output.write((header.toString() + content).getBytes());
        output.flush();

	}
	
}
