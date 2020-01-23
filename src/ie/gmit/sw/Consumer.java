package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * Works in conjuncton with the Producer and Parse classes to build a database from a dataset file.
 * The Consumer class passes the BlockingQueue String elements to the Parse class. Once "exit" is encountered
 * the Consumer class finishes.
 *
 */

public class Consumer implements Runnable{

	private BlockingQueue<String> queue;
	private int nGram;
	
    public Consumer(BlockingQueue<String> q, int k){
        this.queue = q;
        this.nGram = k;
    }
    

    /**
     * The run method loops until "exit" is encountered.
     * Each line is added to an ExecutorService - threadPool size 10.
     * 
     */
    @Override
    public void run() {
        try{
			ExecutorService es = Executors.newFixedThreadPool(10);
            String line;
            //consuming messages until exit message is received
            //consider having this threaded
            while(!(line = queue.take()).equals("exit")){
            	//Thread.sleep(10);
            	//System.out.println("Consumed " + line);
				es.execute(new Parser(line, nGram));
            	//new Thread(new Parser(line, nGram)).start();
            }
            
			es.shutdown();
			try {
				  es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} catch (InterruptedException e) {
				  
			}
		
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer exit.");
    }
    

}