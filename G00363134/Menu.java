import java.util.Scanner;
import java.io.File;

public class Menu{
	public static void main(String[] args){
		
		String pathWiLi;
		//String pathWiLi = new File("").getAbsolutePath();
		String pathFile;
		Scanner sc = new Scanner(System.in);
		//must I ensure the menu is not instanced?

		System.out.println("**************************************************");
		System.out.println("* GMIT - Dept. Compuer Science & Applied Physics *");
		System.out.println("*                                                *");
		System.out.println("*             Text Language Detector             *");
		System.out.println("*                                                *");
		System.out.println("**************************************************");
		
		//enter location of WiLi dataset here - probably worth while creating a language enum for this purpose
		System.out.println("Enter WiLi Data Location");
		//locWiLi = sc.nextLine();
		//pathWiLi += "WiLi\\wili-2018-Small-11750-Edited.txt";
		pathWiLi = "./WiLi/wili-2018-Small-11750-Edited.txt";
		System.out.println("WiLi: " + pathWiLi);
		//readFile.
		//ReadFile rf = new ReadFile(pathWiLi);
		ParseWiLi pf = new ParseWiLi(pathWiLi);
		pf.parseFile();
		
		//enter location of file to query
		System.out.println("Enter Query File Locaion");
		pathFile = sc.nextLine();
		System.out.println("File: " + pathFile);
		
		//process query
		
		//return language
	//*/
	}//main
	
}//Menu-class