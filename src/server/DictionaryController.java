/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.PartOfSpeech;

public class DictionaryController {

	private HashMap<String, HashMap<PartOfSpeech, String>> dictionary;

	public DictionaryController(String dictionaryFilePath) throws Exception {
		this.readDictionary(dictionaryFilePath);
	}

	private void readDictionary(String dictionaryFilePath) throws Exception {
		this.dictionary = new HashMap<String, HashMap<PartOfSpeech, String>>();

		try {
			for (Object wordObject : new JSONArray(new FileReader(dictionaryFilePath))) {
				JSONObject wordJSONObject = (JSONObject) wordObject;
				String word = (String) wordJSONObject.get("word");
				HashMap<PartOfSpeech, String> definitions = new HashMap<>();
				for (Object dfnObject : (JSONArray) wordJSONObject.get("definitions")) {
					JSONObject dfnJSONObject = (JSONObject) dfnObject;

					definitions.put((PartOfSpeech) PartOfSpeech.valueOf((String) dfnJSONObject.get("pos")),
							(String) dfnJSONObject.get("description"));
				}

				this.dictionary.put(word, definitions);
			}
		} catch (NullPointerException e) {
			throw new Exception("Please select the dictionary file");
		} catch (FileNotFoundException e) {
			throw new Exception(String.format("Error: Dictionary file \"%s\" not found", dictionaryFilePath));
		} catch (JSONException e) {
			throw new Exception("Error: Parse error when parsing the dictionary file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized String queryWordMeaning(String word) {
		String response;

		if (this.dictionary.containsKey(word)) {
			response = "";
			HashMap<PartOfSpeech, String> meaning = this.dictionary.get(word);
			for (PartOfSpeech pos : meaning.keySet()) {
				response += String.format("%s%n%8s%s%n%n", pos, "", meaning.get(pos));
			}
		} else {
			response = String.format("Word \"%s\" not found in the dicitonary", word);
		}

		return response;
	}

	public synchronized String addWord(String word, PartOfSpeech pos, String description) {
		String response;
		HashMap<PartOfSpeech, String> definitions;
		if (this.dictionary.containsKey(word)) {
			definitions = this.dictionary.get(word);
			if (definitions.containsKey(pos)) {
				response = String.format("Word \"%s\" of type \"%s\" already exists", word, pos);
			} else {
				response = String.format("New definition with type \"%s\" is added to word \"%s\"", pos.toString(),
						word);
			}
		} else {
			definitions = new HashMap<PartOfSpeech, String>();
			response = String.format("New word \"%s\" is added to the dictionary", "word");
		}

		definitions.put(pos, description);
		this.dictionary.put(word, definitions);

		return response;
	}

	public synchronized String removeWord(String word) {
		String response;

		if (this.dictionary.containsKey(word)) {
			this.dictionary.remove(word);
			response = String.format("Word \"%s\" is removed from the dictionary", word);
		} else {
			response = String.format("Word \"%s\" not found in the dicitonary", word);
		}

		return response;
	}
}
