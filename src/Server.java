import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String [] args) throws Exception {

        ServerSocket ss = new ServerSocket(12345);
        SoundCloud b = new SoundCloud();
        //Notifications n = new Notifications();

        while(true){
            Socket s = ss.accept();
            new Thread(new ServerWorker(s,b)).start();
        }
    }
}
