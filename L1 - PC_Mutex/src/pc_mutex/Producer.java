package pc_mutex;

import java.util.concurrent.locks.Lock;
import static pc_mutex.PC_Mutex.*;

public class Producer extends Thread {

    private final Lock lock;  
    public Producer(Lock lock){
    this.lock = lock;
    }

    public void run() {
        while (true) { 
            try {
                Thread.sleep(1500);     
                lock.lock();
                if (itemStack.size() < Full){                  
                    try {
                        Counter++;                       
                        itemStack.push(String.valueOf(Counter));  
                        System.out.println("producing item "
                                + itemStack.size());
                    } finally {
                        lock.unlock();
                    }
                    
                }
                
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }                                    
        }
    }
}
