/*import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Notifications{

    private final Map<String, Socket> clients;
    private final Lock locker;
    private String message;

    public Notifications() {
        clients = new HashMap<>();
        locker = new ReentrantLock();
    }

    public void addClient(String username, Socket s) {
        locker.lock();
        clients.put(username, s);
        locker.unlock();
    }

    public Map<String, Socket> getClients() {
        return clients;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void notificarTodos(){
        locker.lock();
        for(String client : this.clients.keySet()){
            Socket s = clients.get(client);
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (pw != null) {
                pw.println(message);
                pw.flush();
            }
        }
        locker.unlock();
    }
}*/