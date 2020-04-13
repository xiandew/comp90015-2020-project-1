/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import utils.PartOfSpeech;

public class ClientHandler implements Runnable {

	private static DictionaryController dictionaryController;
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
			this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public static void setupDictionaryController(String dictionaryFilePath) throws Exception {
		try {
			dictionaryController = new DictionaryController(dictionaryFilePath);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void run() {
		String request;
		while (true) {
			try {
				request = this.dataInputStream.readUTF();
				this.dataOutputStream.writeUTF("Received");
				System.out.println(request);
//                if(received.equals("Exit")) {  
//                    System.out.println("Client " + this.s + " sends exit..."); 
//                    System.out.println("Closing this connection."); 
//                    this.s.close(); 
//                    System.out.println("Connection closed"); 
//                    break; 
//                } 

				// creating Date object
//                Date date = new Date(); 

				// write on output stream based on the
				// answer from the client
//                switch (received) { 
//                  
//                    case "Date" : 
//                        toreturn = fordate.format(date); 
//                        dos.writeUTF(toreturn); 
//                        break; 
//                          
//                    case "Time" : 
//                        toreturn = fortime.format(date); 
//                        dos.writeUTF(toreturn); 
//                        break; 
//                          
//                    default: 
//                        dos.writeUTF("Invalid input"); 
//                        break; 
//                }
			} catch (IOException e) {
				System.out.print(e);
				return;
			}
		}

//		try {
//			// closing resources
//			this.dataInputStream.close();
//			this.dataOutputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
