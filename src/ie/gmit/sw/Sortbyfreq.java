package ie.gmit.sw;

import java.util.Comparator;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * LanguageEntry comparator
 * by Frequency
 *
 */

public class Sortbyfreq implements Comparator<LanguageEntry> {

	@Override
	public int compare(LanguageEntry o1, LanguageEntry o2) {
		
		return - Integer.compare(o1.getFrequency(), o2.getFrequency());
		
	}

}
