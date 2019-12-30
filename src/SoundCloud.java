import Exceptions.NomeNaoExisteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SoundCloud{

    private Map<String,User> users;
    private ReentrantLock lockSoundCloud = new ReentrantLock();

    public SoundCloud() {
        this.users = new HashMap<>();
    }

    public void registarUser(String nome, String password){
        this.lockSoundCloud.lock();
        User u = new User(nome,password);
        this.users.put(nome,u);
        this.lockSoundCloud.unlock();
    }

    public boolean login(String nome, String password) throws NomeNaoExisteException{
        if(users.get(nome) == null)
            throw new NomeNaoExisteException("Nome não existe!");
        else{
            return users.get(nome).getPassword().equals(password);
        }
    }

    public String showUsers(){
        return this.users.toString();
    }
}
