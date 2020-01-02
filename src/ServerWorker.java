import Exceptions.MusicaNaoExisteException;
import Exceptions.NomeJaExisteException;
import Exceptions.NomeNaoExisteException;
import Exceptions.PalavraPasseIncorretaException;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ServerWorker implements Runnable{

    private Socket s;
    private SoundCloud sc;
    private User current_user;
    private Notifications notifications;

    public ServerWorker(Socket s, SoundCloud sc, Notifications n){
        this.s = s;
        this.sc = sc;
        this.current_user = null;
        this.notifications = n;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            int filesize = 0;
            String line;
            while (true) {
                String answer = "Comando inválido";
                line = br.readLine();
                if(line == null)
                    break;

                System.out.println(line);
                // Tokenize da linha recebida
                String[] parts = line.split(" ");

                try {
                    if(parts[0].equals("registar")){
                        answer = sc.registarUser(parts[1],parts[2]);
                    }
                    else if(parts[0].equals("login")){
                        this.current_user = sc.login(parts[1],parts[2]);
                        notifications.addClient(parts[1],pw);
                        answer = "Conexão establecida";
                    }
                    else if(parts[0].equals("upload")){
                        if(current_user != null){
                            String[] etiquetas = parts[4].split(",");
                            List<String> wordList = Arrays.asList(etiquetas);
                            answer = String.valueOf(sc.addMusica(parts[1],parts[2],Integer.parseInt(parts[3]),wordList,0));
                            filesize = FileOperations.calculaTam(parts[5]);
                        }
                        else answer = "Login não foi efetuado!";
                    }
                    else if(parts[0].equals("procura")){
                        if(current_user != null){
                            List<Musica> res = sc.procura(parts[1]);
                            answer = res.toString();
                        }
                        else answer = "Login não foi efetuado!";
                    }
                    else if(parts[0].equals("download")){
                        if(current_user != null) {
                            sc.download(Integer.parseInt(parts[1]));
                            String path = "musicas/" + parts[1] + ".mp3";
                            pw.println("ready " + FileOperations.calculaTam(path) + " " + parts[2] + "/" + parts[1] + ".mp3");
                            pw.flush();
                            FileOperations.sendFile(s, path);
                        }
                        else answer = "Login não foi efetuado!";
                    }
                    else if(parts[0].equals("show") && parts[1].equals("users")) // TIRAR DEPOIS ESTE IF E O MÉTODO USADO "showUsers" DA CLASSE SOUNDCLOUD
                        answer = sc.showUsers();
                    else if(parts[0].equals("show") && parts[1].equals("musicas")) // TIRAR DEPOIS ESTE IF E O MÉTODO USADO "showUsers" DA CLASSE SOUNDCLOUD
                        answer = sc.showMusicas();
                }
                catch (NomeNaoExisteException | NomeJaExisteException | MusicaNaoExisteException | PalavraPasseIncorretaException e) {
                    answer = e.getMessage();
                }
                if(!parts[0].equals("download")){
                    pw.println(answer);
                    pw.flush();
                }
                if(parts[0].equals("upload")){
                    //notifications.notifyAll("Notificação! título: " + parts[1] + ", autor: " + parts[2]);
                    FileOperations.saveFile(s,filesize,"musicas/" + answer + ".mp3");
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
