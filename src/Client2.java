import java.io.BufferedReader;
import java.io.IOException;

public class Client2 implements Runnable {

    private BufferedReader br;
    private boolean active = true;

    public Client2(BufferedReader br) {
        this.br = br;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void run() {
        try {
            while(true) {
                if(active) {
                    String answer = br.readLine();
                    System.out.println("Da Thread : " + answer);
                }
                active = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}