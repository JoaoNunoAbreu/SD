import Exceptions.MusicaNaoExisteException;
import Exceptions.NomeJaExisteException;
import Exceptions.NomeNaoExisteException;
import Exceptions.PalavraPasseIncorretaException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SoundCloud{

    private Map<String,User> users;
    private Map<Integer,Musica> musicas;
    private ReentrantLock lockSoundCloud = new ReentrantLock();
    private int lastID = 0;

    public SoundCloud() {
        this.users = new HashMap<>();
        this.musicas = new HashMap<>();
    }

    public String registarUser(String nome, String password) throws NomeJaExisteException{
        try {
            this.lockSoundCloud.lock();
            if(users.containsKey(nome))
                throw new NomeJaExisteException("Nome já existe!");
            else{
                User u = new User(nome,password);
                this.users.put(nome,u);
                return "User criado com sucesso!";
            }
        }
        finally {
            this.lockSoundCloud.unlock();
        }

    }

    public User login(String nome, String password) throws NomeNaoExisteException,PalavraPasseIncorretaException{
        try{
            this.lockSoundCloud.lock();
            if(users.get(nome) == null)
                throw new NomeNaoExisteException("Nome não existe!");
            else
                if(!users.get(nome).getPassword().equals(password))
                    throw new PalavraPasseIncorretaException("Palavra-passe incorrecta!");
                else return users.get(nome);
        }
        finally{
            this.lockSoundCloud.unlock();
        }
    }

    public int addMusica(String titulo, String interprete, Integer ano, List<String> etiquetas, int vezes_descarregada){
        this.lockSoundCloud.lock();
        int id = lastID++;
        Musica m = new Musica(id,titulo,interprete,ano,etiquetas,vezes_descarregada);
        this.musicas.put(id,m);
        this.lockSoundCloud.unlock();
        return id;
    }

    public List<Musica> procura(String etiqueta){
        this.lockSoundCloud.lock();
        List<Musica> res = new ArrayList<>();
        for(Musica m: this.musicas.values()){
            if(m.getEtiquetas().contains(etiqueta))
                res.add(m);
        }
        this.lockSoundCloud.unlock();
        return res;
    }

    public void download(int id) throws MusicaNaoExisteException {
        try {
            this.lockSoundCloud.lock();
            if(!this.musicas.containsKey(id))
                throw new MusicaNaoExisteException("Música não existe.");
            else this.musicas.get(id).downloadHappened();
        }
        finally {
            this.lockSoundCloud.unlock();
        }
    }

    public String showUsers(){
        return this.users.toString();
    }

    public String showMusicas(){
        return this.musicas.toString();
    }
}
