public class NewClient2 implements java.lang.Runnable{

    private SoundCloudRemoto sc;
    private String nome;
    private String pass;
    private RWLock rw;

    public NewClient2(SoundCloudRemoto sc, String nome, String pass, RWLock rw){
        this.sc = sc;
        this.nome = nome;
        this.pass = pass;
        this.rw = rw;
    }

    public void run(){
        try{
            System.out.println(sc.registarUser(nome,pass));
            System.out.println(sc.login(nome,pass));
            rw.writeLock();
            Thread.sleep(1000);
            System.out.println(sc.download(0,"/Users/joaonunoabreu/Desktop/musicas"));
            rw.writeUnlock();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
