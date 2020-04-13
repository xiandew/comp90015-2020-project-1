/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.PartOfSpeech;

public class DictionaryController {

	private HashMap<String, HashMap<PartOfSpeech, String>> dictionary;

	public DictionaryController(String dictionaryFilePath) throws Exception {
		this.readDictionary(dictionaryFilePath);
	}

	private void readDictionary(String dictionaryFilePath) throws Exception {
		this.dictionary = new HashMap<String, HashMap<PartOfSpeech, String>>();

		JSONParser parser = new JSONParser();
		try {
			for (Object wordObject : (JSONArray) parser.parse(new FileReader(dictionaryFilePath))) {
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
		} catch (IOException | ParseException e) {
			throw new Exception("Error: Parse error");
		}
	}

	public void addWord(String word, PartOfSpeech pos, String description) {
		HashMap<PartOfSpeech, String> definitions;
		if (this.dictionary.containsKey(word)) {
			definitions = this.dictionary.get(word);
		} else {
			definitions = new HashMap<PartOfSpeech, String>();
		}
		definitions.put(pos, description);
		this.dictionary.put(word, definitions);
	}

	public void removeWord(String word) throws Exception {
		if (this.dictionary.containsKey(word)) {
			this.dictionary.remove(word);
		} else {
			throw new Exception(String.format("Word \"%s\" not found in the dicitonary", word));
		}
	}
}
