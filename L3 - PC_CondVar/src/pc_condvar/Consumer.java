package pc_condvar;

import java.util.concurrent.locks.Lock;
import static pc_condvar.PC_CondVar.*;

public class Consumer extends Thread {
    
    private final Lock lock;
    final Object condProd;
    final Object condCons;
    
    public Consumer(Lock lock, Object condProd,Object condCons){
    this.lock = lock;
    this.condProd = condProd;
    this.condCons = condCons;
    }
    public void run(){
        while (true) {
            Counter = 0;
            synchronized(condCons){
                if (itemStack.size() == Empty) {                  
                    try {
                        condCons.wait();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized(condProd){
                lock.lock();
                System.out.println("consuming: "+ itemStack.size());
                itemStack.pop();
                lock.unlock();
                condProd.notify();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {                  
            }
        }
    }
}