import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainClient {

    public static void main(String [] args) throws Exception {

        Socket s = new Socket("localhost",12345);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader reader_terminal = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

        int NUM_THREADS = 2;
        Thread[] threads = new Thread[NUM_THREADS];

        threads[0] = new Thread(new Client1(s,br,pw,reader_terminal));
        threads[1] = new Thread(new Client2(br));

        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].start();
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].join();
        }
    }
}
