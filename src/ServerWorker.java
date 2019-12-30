import Exceptions.NomeNaoExisteException;

import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{

    private Socket s;
    private SoundCloud sc;

    public ServerWorker(Socket s, SoundCloud sc){
        this.s = s;
        this.sc = sc;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            while (true) {
                String line;
                String answer = "Comando inválido";
                line = br.readLine();
                if(line == null)
                    break;

                // Tokenize da linha recebida
                String[] parts = line.split(" ");

                try {
                    if(parts[0].equals("registar")){
                        sc.registarUser(parts[1],parts[2]);
                        answer = "User criado com sucesso.";
                    }
                    else if(parts[0].equals("login")){
                        sc.login(parts[1],parts[2]);
                        answer = "User autenticado com sucesso.";
                    }
                    else if(parts[0].equals("show") && parts[1].equals("users")) // TIRAR DEPOIS ESTE IF E O MÉTODO USADO "showUsers" DA CLASSE SOUNDCLOUD
                        answer = sc.showUsers();
                }
                catch (NomeNaoExisteException e) {
                    answer = e.getMessage();
                }
                pw.println(answer);
                pw.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
