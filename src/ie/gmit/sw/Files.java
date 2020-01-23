/*
 * Files should contain 2 filepaths, datasetFile and queryFile 
 *	 
 */

package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * Stores file locations of the dataset and query files.
 *
 */

public class Files {
	private String dataset;
	private String query;
	
	//setters and getters
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * 
	 * Returns the contents of the query file.
	 * 
	 * @param file the file to query.
	 * @return Returns String of query file.
	 */
	public static String getContent(String file) {
		//returns file content as a string
		//	takes filepath arg.
		String line;
		StringBuilder str = new StringBuilder(); 
		
		try{
			BufferedReader br = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(file), "UTF8"));

			while ((line = br.readLine()) != null) {
				str.append(line);
			}

			br.close();
		}
		catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} 
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}		
		
		return str.toString();
	}
	
}
