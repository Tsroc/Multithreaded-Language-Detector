/*
 * Author: Eoin Wilkie
 * 
 * I have an issue with this Runner class. It does not output the correct language although the code is correct.
 * 
 * If I rename this to Runner and rename Runner to Runner2, this class will output the correct language and
 * the other class(Currently Runner) will output an incorrect language.
 * I have no idea why this is the case or where the conflict is. 
 * 
 */
package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * 
 *
 */

public class Runner2 {

	public static void main(String[] args) {
		
		Files file = new Files();
		new Menu(file);
		Database db = new Database();
		Parser.setDB(db);
		int nGram = 3;
	
		ExecutorService es = Executors.newFixedThreadPool(10);

		try {
			BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(file.getDataset())));
			String line = null;
					
			while((line = br.readLine()) != null){
				es.execute(new Parser(line, nGram));
			}

			
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		es.shutdown();
		try {
			  es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			  
		}
		db.resize(300);
		
		Parser.analyseQuery(Files.getContent( file.getQuery() ), nGram);
	}
}
