import Exceptions.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SoundCloudRemoto{

    private Socket s;
    private BufferedReader br;
    private PrintWriter pw;

    public SoundCloudRemoto(String host, int port) throws IOException{
        this.s = new Socket(host,port);
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
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

    public String login(String nome, String pass) throws NomeNaoExisteException, PalavraPasseIncorretaException, SoundCloudRemotoException{
        pw.println("login " + nome + " " + pass);
        pw.flush();
        try {
            String lido = br.readLine();
            if(lido.equals("Nome não existe!"))
                throw new NomeNaoExisteException("Nome não existe!");
            else if(lido.equals("Palavra-passe incorrecta!"))
                throw new PalavraPasseIncorretaException("Palavra-passe incorrecta!");
            else return lido;
        }
        catch(IOException e){
            throw new SoundCloudRemotoException("Não foi possível ler resposta");
        }
    }

    public String procura(String etiqueta) throws SoundCloudRemotoException{
        pw.println("procura " + etiqueta);
        pw.flush();
        try{
            return br.readLine();
        }
        catch(IOException e){
            throw new SoundCloudRemotoException("Não foi possível ler resposta");
        }
    }

    public String upload(String titulo, String interprete, int ano, List<String> etiquetas, String path) throws SoundCloudRemotoException {
        String str_etiquetas = "";
        for(int i = 0; i < etiquetas.size(); i++) {
            if (i == etiquetas.size() - 1)
                str_etiquetas += etiquetas.get(i);
            else str_etiquetas += etiquetas.get(i) + ",";
        }
        pw.println("upload " + titulo + " " + interprete + " " + ano + " " + str_etiquetas + " " + path);
        pw.flush();
        try{
            String lido = br.readLine();
            FileOperations.sendFile(s,path);
            return lido;
        }
        catch(IOException e){
            throw new SoundCloudRemotoException("Não foi possível ler resposta");
        }
    }

    public String download(int id, String path) throws MusicaNaoExisteException,SoundCloudRemotoException{
        String line = "download " + id + " " + path;
        pw.println(line);
        pw.flush();
        try{
            String lido = br.readLine();
            if(lido.equals("Música não existe."))
                throw new MusicaNaoExisteException("Música não existe.");
            else{
                String[] parts = lido.split(" ");
                FileOperations.saveFile(s,Integer.parseInt(parts[1]),parts[2]);
                return lido;
            }
        }
        catch(IOException e){
            throw new SoundCloudRemotoException("Não foi possível ler resposta");
        }
    }
}
