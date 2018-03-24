package pc_mutex;

import java.util.concurrent.locks.Lock;
import static pc_mutex.PC_Mutex.*;

public class Consumer extends Thread {

    private final Lock lock;
 
    public Consumer(Lock lock){
        this.lock = lock;
    }

    public void run() {
	while (true) {                          
            try {     
                if (itemStack.size() != Empty){
                    lock.lock();                
                    try {
                        System.out.println("consuming item "+ itemStack.size());
                        itemStack.pop();
                    } finally {                
                        lock.unlock();
                    }
                    
                }
                Thread.sleep(2500);
                }
            catch (InterruptedException e) {
                 e.printStackTrace();
            }
	
	}
    }
}