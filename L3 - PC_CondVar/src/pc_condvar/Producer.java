package pc_condvar;

import java.util.concurrent.locks.Lock;
import static pc_condvar.PC_CondVar.*;

public class Producer extends Thread {
    
    private final Lock lock;
    private final Object condProd;
    private final Object condCons;
    
    public Producer(Lock lock, Object condProd,Object condCons){
    this.lock = lock;
    this.condProd = condProd;
    this.condCons = condCons;
}
    public void run(){
        while (true) {           
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Counter++;
            synchronized(condProd){
                if (itemStack.size() >= Full) {                   
                    try {                                                
                        condProd.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized(condCons){               
                lock.lock();
                itemStack.push(String.valueOf(Counter));
                System.out.println("producing: " + itemStack.size());
                lock.unlock();
                condCons.notify();              
            }                                       
        }
    }
}