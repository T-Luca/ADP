package pc_sem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.Semaphore;
import static pc_sem.PC_Sem.*;

public class Consumer extends Thread {

    private final Lock lock;
    private Semaphore semFree;
    private Semaphore semFull;
    
    public Consumer(Lock lock,Semaphore semFree, Semaphore semFull){
        this.lock = lock;
        this.semFree = semFree;
        this.semFull = semFull;
    }

    public void run() {
	while (true) {                          
            try {
                semFull.acquire();
                if (itemStack.size() != Empty){
                    lock.lock();                
                    try {
                        System.out.println("consuming item "+ itemStack.size());
                        itemStack.pop();
                    } finally {                
                        lock.unlock();
                    }
                    semFree.release();
                }
                Thread.sleep(2500);
                }
            catch (InterruptedException e) {
                 e.printStackTrace();
            }
	
	}
    }
}