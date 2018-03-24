package pc_sem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.Semaphore;
import static pc_sem.PC_Sem.*;

public class Producer extends Thread {

    private final Lock lock;
    private final Semaphore semFree;
    private final Semaphore semFull;
    
    public Producer(Lock lock, Semaphore semFree, Semaphore semFull){
    this.lock = lock;
    this.semFree = semFree;
    this.semFull = semFull;
    }

    public void run() {
        while (true) { 
            try {
                Thread.sleep(1500);
                semFree.acquire();
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
                    semFull.release();
                }
                
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }                                    
        }
    }
}
