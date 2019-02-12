package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class tftpServer {
	
	public static void main(String[] args) {
		
		//Useless flag used to correct "End never reached Error"
		boolean loop = true;
		int port = 50008;
		
		try{
	        //Create the socket
			ServerSocket serverSocket = new ServerSocket(port); //Instanciando el server socket
	        System.out.println("Socket created on port: " + port);
	        System.out.println("Waiting for connections");
			
			while(loop){
	        	//Wait for connections
	        	Socket clientSocket = serverSocket.accept(); //quedate escuchando hasta que un cliente se comunique contigo.
	        	System.out.println("New Connection from: " + clientSocket.getInetAddress());
	        
	        	//Create the handler for each client connection
				tftpHandler handler = new tftpHandler(clientSocket); 
				//el handler es el thread. le pasas el socket, el Input stream y el output stream.
	        
	        	//Start handler has a thread
	       		handler.start();
			}

	        //Close the server socket
	        serverSocket.close();
	        
	        //OJO!!!
	        //para que esto se repita completo se mete en un while.
	        
			} catch (IOException e) {
		}	
	}
	
	
}

