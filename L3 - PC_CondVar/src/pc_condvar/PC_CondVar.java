package pc_condvar;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PC_CondVar {
        static int Full = 10;
	static int Empty = 0;
	static int Counter = 0;

	
	public static Stack<String> itemStack = new Stack<String>();
        public static Lock lock = new ReentrantLock();
        final static Object condProd = new Object();
	final static Object condCons = new Object();

    public static void main(String[] args) throws InterruptedException {
	Producer producer = new Producer(lock, condProd, condCons);
        Consumer consumer = new Consumer(lock, condProd, condCons);
        
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        
        t1.start();
	t2.start();
        
        t1.join();
	t2.join();
       
       
    }
}
    