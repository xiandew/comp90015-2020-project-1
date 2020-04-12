/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import server.DictionaryController.PartOfSpeech;

public class ClientHandler implements Runnable {

	private static DictionaryController dictionaryController;
	private Socket clientSockete;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	public ClientHandler(Socket clientSocket) throws Exception {
		this.clientSockete = clientSocket;
		try {
			this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
			this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			throw new Exception("Error: Unable to get DataInputStream/DataOutputStream");
		}
	}

	public static void setupDictionaryController(String dictionaryFilePath) throws Exception {
		try {
			dictionaryController = DictionaryController.getInstance(dictionaryFilePath);
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
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			// closing resources
			this.dataInputStream.close();
			this.dataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
