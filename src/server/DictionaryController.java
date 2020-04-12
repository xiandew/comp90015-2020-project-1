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

public class DictionaryController {

	public enum PartOfSpeech {
		CC("Coordinating conjunction"), CD("Cardinal number"), DT("Determiner"), EX("Existential there"),
		FW("Foreign word"), IN("Preposition or subordinating conjunction"), JJ("Adjective"),
		JJR("Adjective, comparative"), JJS("Adjective, superlative"), LS("List item marker"), MD("Modal"),
		NN("Noun, singular or mass"), NNS("Noun, plural"), NNP("Proper noun, singular"), NNPS("Proper noun, plural"),
		PDT("Predeterminer"), POS("Possessive ending"), PRP("Personal pronoun"), PRP$("Possessive pronoun"),
		RB("Adverb"), RBR("Adverb, comparative"), RBS("Adverb, superlative"), RP("Particle"), SYM("Symbol"), TO("to"),
		UH("Interjection"), VB("Verb, base form"), VBD("Verb, past tense"), VBG("Verb, gerund or present participle"),
		VBN("Verb, past participle"), VBP("Verb, non-3rd person singular present"),
		VBZ("Verb, 3rd person singular present"), WDT("Wh-determiner"), WP("Wh-pronoun"), WP$("Possessive wh-pronoun"),
		WRB("Wh-adverb");

		private String description;

		private PartOfSpeech(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return String.format("%s - %s", this.name(), this.description);
		}
	}

	private static DictionaryController dictionaryController = null;
	private HashMap<String, HashMap<PartOfSpeech, String>> dictionary;

	private DictionaryController(String dictionaryFilePath) throws Exception {
		this.readDictionary(dictionaryFilePath);
	}

	public static DictionaryController getInstance(String dictionaryFilePath) throws Exception {
		if (dictionaryController == null) {
			dictionaryController = new DictionaryController(dictionaryFilePath);
		}
		return dictionaryController;
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

					definitions.put((PartOfSpeech) dfnJSONObject.get("pos"), (String) dfnJSONObject.get("description"));
				}

				this.dictionary.put(word, definitions);
			}
		} catch (FileNotFoundException e) {
			throw new Exception(String.format("Error: Dictionary file \"%s\" not found.", dictionaryFilePath));
		} catch (IOException | ParseException e) {
			throw new Exception("Error: Parse error.");
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
			throw new Exception(String.format("Word \"%s\" not found in the dicitonary!", word));
		}
	}
}
