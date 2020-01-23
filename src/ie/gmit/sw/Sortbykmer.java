package ie.gmit.sw;

import java.util.Comparator;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * LanguageEntry comparator
 * by Kmer 
 *
 */

public class Sortbykmer implements Comparator<LanguageEntry>  {

	@Override
	public int compare(LanguageEntry o1, LanguageEntry o2) {
		
		return - Integer.compare(o1.getKmer(), o2.getKmer());
		
	}

}
