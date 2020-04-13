/**
 * @author Xiande Wen (905003)
 */
package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class DictionaryClient {
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	private ClientGUI window;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DictionaryClient();
	}

	public DictionaryClient() {
		// Launch the GUI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				window = new ClientGUI();

				// Setup button action listeners
				window.btnQueryWordMeaning.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							sendRequestToQueryWordMeaning(window.textFieldQueryWordMeaning.getText());
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
					}
				});
				window.btnAddNewWord.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sendRequestToAddNewWord();
					}
				});
				window.btnRemoveWord.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sendRequestToRemoveWord();
					}
				});

				// Connect server through a dialog
				socket = window.connect();
				try {
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}

				// Listen from server
				new Thread(() -> {
					String response;
					while (true) {
						try {
							response = dataInputStream.readUTF();
							System.out.println(response);
						} catch (IOException e) {
							e.printStackTrace();
							return;
						}
					}
				}).start();

				// Launch the main panel
				window.launch();
			}
		});
	}

	public void sendRequestToQueryWordMeaning(String wordQuery) throws IOException {
		dataOutputStream.writeUTF(wordQuery);
	}

	public void sendRequestToAddNewWord() {

	}

	public void sendRequestToRemoveWord() {

	}
}
