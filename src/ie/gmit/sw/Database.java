package ie.gmit.sw;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * Builds a database of Languages which contains a name key and a database of LannguageEntries.
 *
 */

public class Database {
	//can be accessed by many threads.
	private Map<Language, Map<Integer, LanguageEntry>> db = new ConcurrentHashMap<>();
	

	/**
	 * Adds Language entry to Language db.
	 * 
	 * @param s the kmer to be added to the language db.
	 * @param lang the language db to be added to.
	 */
	public void add(CharSequence s, Language lang) {
		int kmer = s.hashCode();
		Map<Integer, LanguageEntry> langDb = getLanguageEntries(lang);
		
		int frequency = 1;
		if (langDb.containsKey(kmer)) {
			frequency += langDb.get(kmer).getFrequency();
		}
		langDb.put(kmer, new LanguageEntry(kmer, frequency));	
	}
	
	
	/**
	 * Returns the language db which has been added with the add() method. 
	 * If language does not exit it is created.
	 * 
	 * @param lang the name of the language to return or create.
	 * @return the language which has been searched for or created.
	 */
	public ConcurrentMap<Integer, LanguageEntry> getLanguageEntries(Language lang){
		ConcurrentMap<Integer, LanguageEntry> langDb = null; 
		if (db.containsKey(lang)) {
			langDb = (ConcurrentMap<Integer, LanguageEntry>) db.get(lang);
		}else {
			langDb = new ConcurrentHashMap<Integer, LanguageEntry>();
			db.put(lang, langDb);
		}
		return langDb;
	}
	
	/**
	 * Resizes the db to a selected size.
	 * 
	 * @param max the size of the db to be returned.
	 */
	public void resize(int max) {
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			Map<Integer, LanguageEntry> top = getTop(max, lang);
			db.put(lang, top);
		}
	}
	
	/**
	 * Used with the resize method.
	 * Is used to add weights to the LanguageEntries and order the db.
	 * 
	 * @param max the size of the resized db, weighs should not be added beyond this number.
	 * @param lang the language of the db.
	 * @return returns an ordered db.
	 */
	public Map<Integer, LanguageEntry> getTop(int max, Language lang) {
		Map<Integer, LanguageEntry> temp = new HashMap<>();
		//Set<LanguageEntry> les = new TreeSet<>(db.get(lang).values());
		//I have replaced Johns code above with the following 2 lines - I think the treemap was not sorted
		//and caused problems with applying rank correctly.
		List<LanguageEntry> les = new ArrayList<>(db.get(lang).values());
		Collections.sort(les, new Sortbyfreq());

		int rank = 1;
		for (LanguageEntry le : les) {
			le.setRank(rank);
			temp.put(le.getKmer(), le);			
			if (rank == max) break;
			rank++;
		}
		
		return temp;
	}
	
	/**
	 * Used to determine the language of an inputed file.
	 * 
	 * @param query map of the query file.
	 * @return	returns closest language to the query file.
	 * 
	 */
	public Language getLanguage(Map<Integer, LanguageEntry> query) {
		TreeSet<OutOfPlaceMetric> oopm = new TreeSet<>();
		
		Set<Language> langs = db.keySet();
		for (Language lang : langs) {
			oopm.add(new OutOfPlaceMetric(lang, getOutOfPlaceDistance(query, db.get(lang))));
		}
		return oopm.first().getLanguage();
	}
	
	/**
	 * Used with getLanguage, determines the map args distance from the db languages.
	 * 
	 * @param query map of the query file.
	 * @param subject 
	 * @return returns the distance
	 */
	private int getOutOfPlaceDistance(Map<Integer, LanguageEntry> query, Map<Integer, LanguageEntry> subject) {
		int distance = 0;
		
		Set<LanguageEntry> les = new TreeSet<>(query.values());		
		for (LanguageEntry q : les) {
			LanguageEntry s = subject.get(q.getKmer());
			if (s == null) {
				distance += subject.size() + 1;
			}else {
				distance += s.getRank() - q.getRank();
			}
		}
		return distance;
	}
	
	/**
	 * @author korzt
	 * 
	 * class is used to measure distance from languages.
	 * Implements comparable to order the results and find the closest.
	 */
	private class OutOfPlaceMetric implements Comparable<OutOfPlaceMetric>{
		private Language lang;
		private int distance;
		
		public OutOfPlaceMetric(Language lang, int distance) {
			super();
			this.lang = lang;
			this.distance = distance;
		}

		public Language getLanguage() {
			return lang;
		}

		public int getAbsoluteDistance() {
			return Math.abs(distance);
		}

		/**
		 * Compare by distance.
		 * 
		 */
		@Override
		public int compareTo(OutOfPlaceMetric o) {
			return Integer.compare(this.getAbsoluteDistance(), o.getAbsoluteDistance());
		}

		@Override
		public String toString() {
			return "[lang=" + lang + ", distance=" + getAbsoluteDistance() + "]";
		}
	}
}