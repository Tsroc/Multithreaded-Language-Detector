package ie.gmit.sw;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * Runs the program.
 *
 */

public class Runner {
	public static void main(String[] args) {
		
		Files file = new Files();
		new Menu(file);
		
        //Creating BlockingQueue of size 10
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        
        
		//Parser.setDB(db);
        int nGram = 3;
		
        Producer producer = new Producer(file.getDataset(), queue);
        Consumer consumer = new Consumer(queue, nGram);
		Database db = new Database();
		Parser.setDB(db);
        
        System.out.println("Producer and Consumer has started.");
        
        //starting producer to produce messages in queue
        Thread t1 = new Thread(producer);
        t1.start();
        //starting consumer to consume messages from queue
        Thread t2 = new Thread(consumer);
        t2.start();
	
 
        try {
        	t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        System.out.println("Producer and Consumer has finished.");
        
		db.resize(300);
		
		Parser.analyseQuery(Files.getContent( file.getQuery() ), nGram);
	}
}
