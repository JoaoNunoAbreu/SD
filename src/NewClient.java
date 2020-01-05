import Exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class NewClient implements java.lang.Runnable{

    private SoundCloudRemoto sc;
    private String nome;
    private String pass;
    private RWLock rw;

    public NewClient(SoundCloudRemoto sc, String nome, String pass, RWLock rw){
        this.sc = sc;
        this.nome = nome;
        this.pass = pass;
        this.rw = rw;
    }

    public void run(){
        try{
            System.out.println(sc.registarUser(nome,pass));
            System.out.println(sc.login(nome,pass));
            List<String> etiquetas = new ArrayList<>();
            etiquetas.add("rock");
            etiquetas.add("pop");
            String path = "/Users/joaonunoabreu/Desktop/AlanWalker_Faded.mp3";
            String id = sc.upload("titulo","interprete",1999,etiquetas,path);
            System.out.println(id);
            System.out.println(sc.download(Integer.parseInt(id),"/Users/joaonunoabreu/Desktop/musicas"));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
