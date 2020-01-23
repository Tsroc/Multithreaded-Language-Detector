package ie.gmit.sw;

import java.util.Scanner;

/**
 * 
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * A menu displayed to the user to read the dataset and query file inputs.
 *
 */

public class Menu{
	
	public Menu(Files file) {
		this.display(file);
	}
	
	public void display(Files file) {		
		Scanner sc = new Scanner(System.in);

		System.out.println("**************************************************");
		System.out.println("* GMIT - Dept. Compuer Science & Applied Physics *");
		System.out.println("*                                                *");
		System.out.println("*             Text Language Detector             *");
		System.out.println("*                                                *");
		System.out.println("**************************************************");
		
		System.out.println("Enter WiLi Data Location (wili-2018-Small-11750-Edited.txt)");
		file.setDataset("wili-2018-Small-11750-Edited.txt");
		//file.setDataset(sc.nextLine());
				
		//enter location of file to query
		System.out.println("Enter Query File Locaion");
		file.setQuery("Russian.txt");
		//file.setQuery(sc.nextLine());
		
		sc.close();
	}//display()
	
}//Menu-class