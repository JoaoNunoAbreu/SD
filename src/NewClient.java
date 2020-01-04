import Exceptions.NomeJaExisteException;
import Exceptions.NomeNaoExisteException;
import Exceptions.PalavraPasseIncorretaException;
import Exceptions.SoundCloudRemotoException;

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
            String path = "/Users/joaonunoabreu/Desktop/teste.txt";
            System.out.println(sc.upload("titulo","interprete",1999,etiquetas,path));
        }
        catch(NomeJaExisteException | SoundCloudRemotoException | NomeNaoExisteException | PalavraPasseIncorretaException e){
            System.out.println(e.getMessage());
        }
    }
}
