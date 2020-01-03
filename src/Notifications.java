import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Notifications{

    private final Map<String, PrintWriter> clients;
    private final Lock locker;
    private String message;

    public Notifications() {
        clients = new HashMap<>();
        locker = new ReentrantLock();
    }

    public void addClient(String username, PrintWriter pw) {
        locker.lock();
        clients.put(username, pw);
        locker.unlock();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void notificarTodos(){
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
}