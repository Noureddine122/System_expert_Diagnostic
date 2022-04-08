package fstm.projet.controller;

import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {

	   public static void main(String[] args) throws Exception
	    {
	        ServerSocket ss = new ServerSocket(6000);
	        System.out.println("ServerSocket awaiting connections...");
	        new StreamController().start();
	        while(true) {
				
	        	 Socket socket  = ss.accept();
	        	 synchronized(socket){
					 new SocketController(socket).run();

	        	 }
	 	       
	 	
	        }
	       
}

}
