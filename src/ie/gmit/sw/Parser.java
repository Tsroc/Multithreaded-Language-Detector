package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * Used to parse the lines of the dataset file into kmers associated with a language and add to the database.
 *
 */

public class Parser implements Runnable {
	private static Database db = null;
	private String line;
	private int k;
		
	//constructor
	public Parser(String line, int k) {
		this.line = line;
		this.k = k;		
	}

	
	/**
	 * Links database in Runner with database built by the Parse Class.
	 * 
	 * @param db
	 */
	public static void setDB(Database db) {
		Parser.db = db;
	}
	
	//when a new thread is created.
	@Override
	public void run() {
		String[] record = line.trim().split("@");
		
		if(record.length == 2) {
			parse(record[0], record[1]);
		}
	}
	
	/**
	 * Each line is parsed here. Split into kmers and added to the database.
	 * 
	 * @param text the text from each line in the dataset.
	 * @param lang the language of each line in the dataset.
	 * @param ks
	 */
	private void parse(String text, String lang, int... ks) {
		Language language = Language.valueOf(lang);
	
		for(int i = 0; i <= text.length() - k; i++) {
			CharSequence kmer = text.substring(i, i + k);
			Parser.db.add(kmer, language);
		}	
	}
	
	/**
	 * Used to determine the language of the query file.
	 * It is added to a map and then compared against entries in the database to find the closest match.
	 * 
	 * @param text the query file.
	 * @param k the nGram size.
	 */
	public static void analyseQuery(String text, int k) {
		//create data structure to hold the new n-Gram from unidentified language.
		Map<Integer, LanguageEntry> db = new HashMap<>();
		
		for(int i = 0; i <= text.length() - k; i++) {
			CharSequence cKmer = text.substring(i, i + k);
				
			int kmer = cKmer.hashCode();
			int frequency = 1;
			if (db.containsKey(kmer)) {
				frequency += db.get(kmer).getFrequency();
			}
			db.put(kmer, new LanguageEntry(kmer, frequency));
		}	
		
		ArrayList<LanguageEntry> sortedDB = new ArrayList<>(db.values());
		Collections.sort(sortedDB, new Sortbyfreq());
		
		//get top 300 spots and assign a rank to.
		Map<Integer, LanguageEntry> temp = new HashMap<>();
		int rank = 1;
		for (LanguageEntry le: sortedDB) {
			
			le.setRank(rank);
			temp.put(le.getKmer(), le);
			if (rank == 300)
				break;
			rank++;
		}
		db = temp;

		System.out.println( Parser.db.getLanguage(temp) );
		
	}
}
