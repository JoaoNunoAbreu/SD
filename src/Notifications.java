import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Notifications {

    private final Map<String, PrintWriter> clients;
    private final Lock locker;


    public Notifications() {
        clients = new HashMap<>();
        locker = new ReentrantLock();
    }


    public void addClient(String username, PrintWriter pw) {
        locker.lock();
        clients.put(username, pw);
        locker.unlock();
    }


    public void notify(String username, String message) {
        locker.lock();

        PrintWriter pw = clients.get(username);

        locker.unlock();

        if (pw != null) {
            pw.println(message);
            pw.flush();
        }
    }

    public void notifyAll(String message){
        locker.lock();
        for(String client : this.clients.keySet()){
            PrintWriter pw = clients.get(client);
            if (pw != null) {
                pw.println(message);
                pw.flush();
            }
        }
        locker.unlock();
    }

    public void remove(String username) {
        locker.lock();
        clients.remove(username);
        locker.unlock();
    }
}