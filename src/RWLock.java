import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {

    private int writers;
    private ReentrantLock l = new ReentrantLock();
    private Condition waitWriter = l.newCondition();
    private final int MAXDOWN = 5;

    public RWLock(){
        this.writers = 0;
    }

    public void writeLock() throws InterruptedException {
        this.l.lock();
        long start = System.currentTimeMillis();
        while(this.writers > MAXDOWN){
            this.waitWriter.await();
        }
        System.out.println("Writer esperou " + (System.currentTimeMillis() - start)+ " ms");
        this.writers++;
        this.l.unlock();
    }
    public void writeUnlock(){
        this.l.lock();
        this.writers--;
        this.waitWriter.signalAll();
        this.l.unlock();
    }
}
