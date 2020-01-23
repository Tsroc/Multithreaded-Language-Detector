package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;


/**
 * @author Eoin Wilkie 
 * @version 1.0
 * @since 1.10
 * 
 * Works in conjuncton with the Producer and Parse classes to build a database from a dataset file.
 * The Producer class takes the dataset and adds each line to a BlockingQueue, when there are no remaining lines
 * "exit" is added and the Producer class finishes. 
 *
 */

public class Producer implements Runnable {

	private String dataset;
    private BlockingQueue<String> queue;
    
    public Producer(String dataset, BlockingQueue<String> q){
    	this.dataset = dataset;
		this.queue = q;
    }

    /**
     * The run method loops, adding each line to the BlockingQueue until no further lines are read
     * from the dataset file and then adds "exit" to the Blockning Queue
     * 
     */
    @Override
    public void run() {
        //produce messages
     	try {
			BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream(this.dataset)));
			String line = null;
								
			while((line = br.readLine()) != null){
				queue.put(line);
				//System.out.println("Produced" + line);
			}
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
     	
        //adding exit message
        String msg = new String("exit");
        try {
            queue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Producer exit.");
    }

}