import java.io.IOException;

public class Main {

    public static void main(String [] args) throws Exception {

        int NUM_THREADS = 10;
        Thread[] threads = new Thread[NUM_THREADS];
        SoundCloudRemoto sc = new SoundCloudRemoto("localhost",12345);

        for(int i = 0; i < NUM_THREADS; i++){
            threads[i] = new Thread(new NewClient(sc,String.valueOf(i),String.valueOf(i)));
        }

        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].start();
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].join();
        }
    }
}
