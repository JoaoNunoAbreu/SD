public class Main {

    public static void main(String [] args) throws Exception {

        int NUM_THREADS = 11;
        Thread[] threads = new Thread[NUM_THREADS];
        SoundCloudRemoto[] stubs = new SoundCloudRemoto[NUM_THREADS];
        RWLock rwLock = new RWLock();

        for(int i = 0; i < NUM_THREADS; i++){
            stubs[i] = new SoundCloudRemoto("localhost",12345);
        }
        /*for(int i = 0; i < NUM_THREADS; i++){
            threads[i] = new Thread(new NewClient(stubs[i],String.valueOf(i),String.valueOf(i)));
        }*/
        threads[0] = new Thread(new NewClient(stubs[0],String.valueOf(0),String.valueOf(0),rwLock));

        for(int i = 1; i <= 10; i++) {
            threads[i] = new Thread(new NewClient2(stubs[i], String.valueOf(i), String.valueOf(i), rwLock));
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].start();
        }
        for(int i = 0; i < NUM_THREADS; i++){
            threads[i].join();
        }
    }
}
