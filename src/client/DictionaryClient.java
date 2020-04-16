/**
 * @author Xiande Wen (905003)
 */
package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import utils.MessageType;
import utils.PartOfSpeech;

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
						sendRequestToQueryWordMeaning();
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
				socket = connect();
				try {
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}

				// Listen from server
				new Thread(() -> {
					JSONObject response;
					while (true) {
						try {
							response = new JSONObject(dataInputStream.readUTF());
							MessageType type = MessageType.valueOf((String) response.get("type"));

							switch (type) {
							case ResponseWordMeaning:
								window.textAreaWordMeaningOutput.setText((String) response.get("data"));
								break;

							case ResponseAddingNewWord:
								window.textAreaAddingNewWordOutput.setText((String) response.get("data"));
								break;

							case ResponseRemovingWord:
								window.textAreaRemoveWordOutput.setText((String) response.get("data"));
								break;

							case ResponseConnectionQueued:
								window.showMessageDialog((String) response.get("data"));
								break;

							default:
								System.out.println("Invalid message type");
								break;
							}

						} catch (EOFException e) {
							break;
						} catch (IOException e) {
							e.printStackTrace();
							break;
						}
					}
				}).start();

				// Launch the main panel
				window.launch();
			}
		});
	}

	public Socket connect() {
		Socket socket = null;
		do {
			int option = JOptionPane.showConfirmDialog(null, window.panelConnectServer, "Connect to dictionary server",
					JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					socket = new Socket(window.textFieldEnterServerAddr.getText(),
							Integer.parseInt(window.textFieldEnterPortNumber.getText()));
				} catch (UnknownHostException e) {
					window.showMessageDialog("Make sure that your server address is correct");
				} catch (NumberFormatException e) {
					window.showMessageDialog("Port number must be numerical");
				} catch (ConnectException e) {
					window.showMessageDialog("Error: Server error");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		} while (socket == null);
		return socket;
	}

	public void sendRequestToQueryWordMeaning() {
		String word = window.textFieldQueryWordMeaning.getText();

		if (!this.validateWordInput(word)) {
			return;
		}

		HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("type", MessageType.RequestWordMeaning.toString());
		msg.put("data", word);

		sendRequest(new JSONObject(msg).toString());
	}

	public void sendRequestToAddNewWord() {
		String word = window.textFieldEnterNewWord.getText();
		PartOfSpeech selectedWordType = (PartOfSpeech) window.comboBoxSelectWordType.getSelectedItem();
		String description = window.textAreaEnterWordDefinition.getText();

		if (!this.validateWordInput(word)) {
			return;
		}

		if (selectedWordType == null) {
			window.showMessageDialog("Please select the word type");
			return;
		}

		if (description.isEmpty()) {
			window.showMessageDialog("Please enter the word definition");
			return;
		}

		HashMap<String, String> newWord = new HashMap<String, String>();
		newWord.put("word", word);
		newWord.put("wordType", selectedWordType.name());
		newWord.put("description", description);

		HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("type", MessageType.RequestAddingNewWord.toString());
		msg.put("data", new JSONObject(newWord).toString());
		sendRequest(new JSONObject(msg).toString());
	}

	public void sendRequestToRemoveWord() {
		String word = window.textFieldWordToRemove.getText();

		if (!this.validateWordInput(word)) {
			return;
		}

		HashMap<String, String> msg = new HashMap<String, String>();
		msg.put("type", MessageType.RequestRemovingWord.toString());
		msg.put("data", word);

		sendRequest(new JSONObject(msg).toString());
	}

	private void sendRequest(String msg) {
		try {
			dataOutputStream.writeUTF(msg);
		} catch (IOException e) {
			// e.printStackTrace();
			window.showMessageDialog("Error: Unable to send message. Server might be stopped");
		}
	}

	private boolean validateWordInput(String word) {
		if (word.isEmpty()) {
			window.showMessageDialog("Please enter the word");
			return false;
		}

		if (!word.matches("[a-zA-Z]+")) {
			window.showMessageDialog("The word must be letters only");
			return false;
		}

		return true;
	}
}
