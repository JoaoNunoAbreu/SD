public class Main {

    public static void main(String [] args) throws Exception {

        int NUM_THREADS = 10;
        Thread[] threads = new Thread[NUM_THREADS];
        SoundCloudRemoto[] stubs = new SoundCloudRemoto[NUM_THREADS];

        for(int i = 0; i < NUM_THREADS; i++){
            stubs[i] = new SoundCloudRemoto("localhost",12345);
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i] = new Thread(new NewClient(stubs[i],String.valueOf(i),String.valueOf(i)));
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].start();
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].join();
        }
    }
}
