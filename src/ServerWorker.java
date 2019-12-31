import Exceptions.NomeJaExisteException;
import Exceptions.NomeNaoExisteException;

import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{

    private Socket s;
    private SoundCloud sc;

    private static int MAXSIZE = 524288; // 0.5 Mb = 500 Kb

    public ServerWorker(Socket s, SoundCloud sc){
        this.s = s;
        this.sc = sc;
    }

    private void saveFile(String size) throws IOException {
        DataInputStream dis = new DataInputStream(s.getInputStream());
        FileOutputStream fos = new FileOutputStream("musicas/testfile.txt");
        byte[] buffer = new byte[MAXSIZE];

        int filesize = Integer.parseInt(size); // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }
        fos.close();
        dis.close();
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
                        answer = sc.registarUser(parts[1],parts[2]);
                    }
                    else if(parts[0].equals("login")){
                        answer = String.valueOf(sc.login(parts[1],parts[2]));
                    }
                    else if(parts[0].equals("upload")){
                        answer = "Upload permitido";
                        pw.println(answer);
                        pw.flush();
                        saveFile(parts[1]);
                    }
                    else if(parts[0].equals("show") && parts[1].equals("users")) // TIRAR DEPOIS ESTE IF E O MÉTODO USADO "showUsers" DA CLASSE SOUNDCLOUD
                        answer = sc.showUsers();
                    else if(parts[0].equals("show") && parts[1].equals("musicas")) // TIRAR DEPOIS ESTE IF E O MÉTODO USADO "showUsers" DA CLASSE SOUNDCLOUD
                        answer = sc.showMusicas();

                }
                catch (NomeNaoExisteException | NomeJaExisteException e) {
                    answer = e.getMessage();
                }
                if(!parts[0].equals("upload")) {
                    pw.println(answer);
                    pw.flush();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
