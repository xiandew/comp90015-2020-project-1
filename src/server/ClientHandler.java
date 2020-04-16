/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import utils.MessageType;
import utils.PartOfSpeech;

public class ClientHandler implements Runnable {

	private static DictionaryController dictionaryController;
	private Socket clientSocket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			this.dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
			this.dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
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
		JSONObject request;
		HashMap<String, String> response = new HashMap<>();
		while (true) {
			try {
				request = new JSONObject(this.dataInputStream.readUTF());
			} catch (JSONException e) {
				e.printStackTrace();
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			MessageType type = MessageType.valueOf((String) request.get("type"));

			// Get response
			switch (type) {
			case RequestWordMeaning:

				response = new HashMap<>();
				response.put("type", MessageType.ResponseWordMeaning.toString());
				response.put("data", ClientHandler.dictionaryController.queryWordMeaning((String) request.get("data")));

			case RequestAddingNewWord:

				response = new HashMap<>();
				response.put("type", MessageType.ResponseAddingNewWord.toString());

				JSONObject newWord = new JSONObject(request.get("data"));
				String word = (String) newWord.get("word");
				PartOfSpeech wordType = PartOfSpeech.valueOf((String) newWord.get("wordType"));
				String description = (String) newWord.get("description");

				response.put("data", ClientHandler.dictionaryController.addWord(word, wordType, description));

			case RequestRemovingWord:

				response = new HashMap<>();
				response.put("type", MessageType.ResponseRemovingWord.toString());
				response.put("data", ClientHandler.dictionaryController.removeWord((String) request.get("data")));

			default:
				System.out.println("Invalid message type");
				break;
			}

			// Send response
			try {
				this.dataOutputStream.writeUTF(new JSONObject(response).toString());
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				break;
			}
		}

		try {
			// closing resources
			this.clientSocket.close();
			this.dataInputStream.close();
			this.dataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
