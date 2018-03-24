package pc_sem;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PC_Sem {
    
        static int Full = 10;
	static int Empty = 0;
	static int Counter = 0;

	public static Lock lock = new ReentrantLock();
	public static Stack<String> itemStack = new Stack<String>();

    public static void main(String[] args) throws InterruptedException {
        Semaphore semFull = new Semaphore(0);
        Semaphore semFree = new Semaphore(10);
	Producer producer = new Producer(lock, semFree, semFull);
        Consumer consumer = new Consumer(lock, semFree, semFull);
        
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        
        t1.start();
	t2.start();
        
        t1.join();
	t2.join();
    }
    
}
