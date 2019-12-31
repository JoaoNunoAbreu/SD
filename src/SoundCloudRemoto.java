import Exceptions.NomeJaExisteException;
import Exceptions.NomeNaoExisteException;
import Exceptions.SoundCloudRemotoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SoundCloudRemoto{

    private BufferedReader br;
    private PrintWriter pw;

    public SoundCloudRemoto(String host, int port) throws IOException{
        Socket s = new Socket(host,port);
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.pw = new PrintWriter(s.getOutputStream());
    }

    public String registarUser(String nome, String pass) throws NomeJaExisteException,SoundCloudRemotoException{
        pw.println("registar " + nome + " " + pass);
        pw.flush();
        try{
            String lido = br.readLine();
            if (lido.equals("Nome já existe!"))
                throw new NomeJaExisteException("Nome já existe!");
            return lido;
        }
        catch(IOException e){
            throw new SoundCloudRemotoException("Não foi possível ler resposta");
        }
    }

    public boolean login(String nome, String pass) throws NomeNaoExisteException, SoundCloudRemotoException{
        pw.println("login " + nome + " " + pass);
        pw.flush();
        try {
            String lido = br.readLine();
            if(lido.equals("Nome não existe!"))
                throw new NomeNaoExisteException("Nome não existe!");
            else return Boolean.parseBoolean(lido);
        }
        catch(IOException e){
            throw new SoundCloudRemotoException("Não foi possível ler resposta");
        }
    }
}
