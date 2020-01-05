import Exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class NewClient implements java.lang.Runnable{

    private SoundCloudRemoto sc;
    private String nome;
    private String pass;

    public NewClient(SoundCloudRemoto sc, String nome, String pass){
        this.sc = sc;
        this.nome = nome;
        this.pass = pass;
    }

    public void run(){
        try{
            System.out.println(sc.registarUser(nome,pass));
            System.out.println(sc.login(nome,pass));
            List<String> etiquetas = new ArrayList<>();
            etiquetas.add("rock");
            etiquetas.add("pop");
            String path = "/Users/joaonunoabreu/Desktop/AlanWalker_Faded.mp3";
            System.out.println(sc.upload("titulo","interprete",1999,etiquetas,path));
            System.out.println(sc.download(Integer.parseInt(nome),"/Users/joaonunoabreu/Desktop/musicas"));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
