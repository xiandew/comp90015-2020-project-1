/**
 * @author Xiande Wen (905003)
 */
package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import utils.PartOfSpeech;

public class DictionaryController {

	private String dictionaryFilePath;
	private HashMap<String, HashMap<PartOfSpeech, TreeSet<String>>> dictionary;

	public DictionaryController(String dictionaryFilePath) throws Exception {
		this.dictionaryFilePath = dictionaryFilePath;
		this.readDictionary(dictionaryFilePath);
	}

	private void readDictionary(String dictionaryFilePath) throws Exception {
		this.dictionary = new HashMap<String, HashMap<PartOfSpeech, TreeSet<String>>>();

		try {
			JSONTokener wordObjectsJSONTokener = new JSONTokener(new FileReader(dictionaryFilePath));

			if (!wordObjectsJSONTokener.more()) {
				return;
			}

			for (Object wordObject : new JSONArray(wordObjectsJSONTokener)) {
				JSONObject wordJSONObject = (JSONObject) wordObject;
				String word = (String) wordJSONObject.get("word");

				HashMap<PartOfSpeech, TreeSet<String>> definitions = new HashMap<>();
				for (Object dfnObject : (JSONArray) wordJSONObject.get("definitions")) {
					JSONObject dfnJSONObject = (JSONObject) dfnObject;

					JSONArray descriptsJSONArray = (JSONArray) dfnJSONObject.get("descriptions");
					TreeSet<String> descripts = new TreeSet<String>();
					for (int i = 0; i < descriptsJSONArray.length(); i++) {
						descripts.add(descriptsJSONArray.getString(i));
					}

					definitions.put((PartOfSpeech) PartOfSpeech.valueOf((String) dfnJSONObject.get("pos")), descripts);
				}

				this.dictionary.put(word, definitions);
			}
		} catch (NullPointerException e) {
			throw new Exception("Please select the dictionary file");
		} catch (FileNotFoundException e) {
			throw new Exception(String.format("Error: Dictionary file \"%s\" not found", dictionaryFilePath));
		} catch (JSONException e) {
			// e.printStackTrace();
			throw new Exception("Error: Parse error when parsing the dictionary file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized String queryWordMeaning(String word) {
		String response;

		word = word.toLowerCase();

		if (this.dictionary.containsKey(word)) {
			response = "";
			HashMap<PartOfSpeech, TreeSet<String>> meaning = this.dictionary.get(word);
			for (PartOfSpeech pos : meaning.keySet()) {
				response += String.format("• %s%n", pos);

				int nDescript = 1;
				for (String descript : meaning.get(pos)) {
					response += String.format("%8s[%d] %s%n", "", nDescript++, descript);
				}

				response += String.format("%n");
			}
		} else {
			response = String.format("Word \"%s\" not found in the dicitonary", word);
		}

		return response;
	}

	public synchronized String addWord(String word, PartOfSpeech pos, String description) {
		String response;
		HashMap<PartOfSpeech, TreeSet<String>> definitions;
		TreeSet<String> descripts;

		word = word.toLowerCase();

		if (this.dictionary.containsKey(word)) {
			definitions = this.dictionary.get(word);
			if (definitions.containsKey(pos)) {
				descripts = definitions.get(pos);
				if (descripts.add(description)) {
					response = String.format("New description is added to word \"%s\" of type \"%s\"", word, pos);
				} else {
					response = String.format("Word \"%s\" of type \"%s\" with such description already exists", word,
							pos);
				}
			} else {
				descripts = new TreeSet<String>();
				descripts.add(description);
				response = String.format("New definition with type \"%s\" is added to word \"%s\"", pos, word);
			}
		} else {
			definitions = new HashMap<PartOfSpeech, TreeSet<String>>();
			descripts = new TreeSet<String>();
			descripts.add(description);
			response = String.format("New word \"%s\" is added to the dictionary", word);
		}

		definitions.put(pos, descripts);
		this.dictionary.put(word, definitions);

		return response + String.format("%n---%n") + this.queryWordMeaning(word);
	}

	public synchronized String removeWord(String word) {
		String response;

		word = word.toLowerCase();

		if (this.dictionary.containsKey(word)) {
			response = String.format("Word \"%s\" is removed from the dictionary%n---%n%s", word,
					this.queryWordMeaning(word));
			this.dictionary.remove(word);
		} else {
			response = String.format("Word \"%s\" not found in the dicitonary", word);
		}

		return response;
	}

	public void saveToDisk() {
		JSONArray dictionary = new JSONArray();
		for (String word : this.dictionary.keySet()) {
			JSONObject wordJSONObject = new JSONObject();
			wordJSONObject.put("word", word);
			JSONArray dfnJSONObjects = new JSONArray();
			HashMap<PartOfSpeech, TreeSet<String>> definitions = this.dictionary.get(word);
			for (PartOfSpeech pos : definitions.keySet()) {
				JSONObject dfnJSONObject = new JSONObject();
				dfnJSONObject.put("pos", pos.name());

				JSONArray descriptsJSONArray = new JSONArray();
				for (String descript : definitions.get(pos)) {
					descriptsJSONArray.put(descript);
				}

				dfnJSONObject.put("descriptions", descriptsJSONArray);
				dfnJSONObjects.put(dfnJSONObject);
			}
			wordJSONObject.put("definitions", dfnJSONObjects);
			dictionary.put(wordJSONObject);
		}

		try (PrintWriter dictionaryPrintWtriter = new PrintWriter(this.dictionaryFilePath, "UTF-8");) {
			dictionaryPrintWtriter.println(dictionary.toString(4));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
