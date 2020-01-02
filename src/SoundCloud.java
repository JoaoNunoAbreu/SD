import Exceptions.MusicaNaoExisteException;
import Exceptions.NomeJaExisteException;
import Exceptions.NomeNaoExisteException;

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
        this.lockSoundCloud.lock();
        if(users.containsKey(nome)) {
            this.lockSoundCloud.unlock();
            throw new NomeJaExisteException("Nome já existe!");
        }
        else{
            User u = new User(nome,password);
            this.users.put(nome,u);
            this.lockSoundCloud.unlock();
            return "User criado com sucesso!";
        }
    }

    public boolean login(String nome, String password) throws NomeNaoExisteException{
        this.lockSoundCloud.lock();
        if(users.get(nome) == null){
            this.lockSoundCloud.unlock();
            throw new NomeNaoExisteException("Nome não existe!");
        }
        else{
            this.lockSoundCloud.unlock();
            return users.get(nome).getPassword().equals(password);
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
        this.lockSoundCloud.lock();
        if(!this.musicas.containsKey(id)){
            this.lockSoundCloud.unlock();
            throw new MusicaNaoExisteException("Música não existe.");
        }
        else{
            this.musicas.get(id).downloadHappened();
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
