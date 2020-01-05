import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {

    private int readers;
    private int writers;
    ReentrantLock l = new ReentrantLock();
    Condition waitWriter = l.newCondition();
    Condition waitReader = l.newCondition();

    public RWLock(){
        this.readers = 0;
        this.writers = 0;
    }

    public void readLock() throws InterruptedException {
        this.l.lock();
        long start = System.currentTimeMillis();
        while(this.writers > 0){
            this.waitReader.await();
        }
        System.out.println("Reader esperou " + (System.currentTimeMillis() - start) + " ms");
        this.readers++;
        this.l.unlock();

    }
    public void readUnlock(){
        this.l.lock();
        this.readers--;
        if(readers == 0)
            this.waitWriter.signal();
        this.l.unlock();
    }
    public void writeLock() throws InterruptedException {
        this.l.lock();
        long start = System.currentTimeMillis();
        while(this.readers > 0 || this.writers > 0){
            this.waitWriter.await();
        }
        System.out.println("Writer esperou " + (System.currentTimeMillis() - start)+ " ms");
        this.writers = 1;
        this.l.unlock();
    }
    public void writeUnlock(){
        this.l.lock();
        this.writers = 0;
        this.waitWriter.signal();
        this.waitReader.signalAll();
        this.l.unlock();
    }
}
