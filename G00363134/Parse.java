import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

public class Parse implements Parserator{

	String fileIn;

		//constructor
    public Parse(String fileIn){
				this.fileIn = fileIn;

				this.parseFile();
				this.fileOut();
		}
		
	public void parseFile(){

		String line;
		String[] words;   

		try{
			BufferedReader br = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(this.fileIn), "UTF8"));

			while ((line = br.readLine()) != null) {
				//parsing file line by line: what should be done?
				System.out.println(line);

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
	}//parseFile()-end

	/*
		For testing purposes.
			To ensure that characters are being read correctly. 
	*/
	public void fileOut(){
		String line;
		java.io.OutputStreamWriter fw;
		try{
			fw = new java.io.OutputStreamWriter(new java.io.FileOutputStream("./testout.txt"), StandardCharsets.UTF_8);

			BufferedReader br = new BufferedReader(
				new InputStreamReader(
					new FileInputStream(this.fileIn), "UTF8"));

			while ((line = br.readLine()) != null) {

				fw.write(line);

			}

			br.close();
			fw.close();
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
	}
}//class:ParserFile-end