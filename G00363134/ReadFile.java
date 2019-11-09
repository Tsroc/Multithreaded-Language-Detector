import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class ReadFile{

	File file;
	Scanner sc;

	public ReadFile(String file){
		this.file = new File(file);
		//sc = new Scanner(file);
		
		System.out.println(file);
		this.readFile(this.file);
	}
	
	private void readFile(File file){
		try{
			Charset cs = StandardCharsets.UTF_8;
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, cs);
			BufferedReader br = new BufferedReader(isr);

			String line;
			while((line = br.readLine()) != null){
				 //process the line
				 System.out.println(line);
			}
			br.close();
		}
		catch(Exception IOException){
			
		}


	}
}