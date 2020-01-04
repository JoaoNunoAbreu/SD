import java.io.*;
import java.net.Socket;

public class Client1 implements Runnable{

    private Socket s;
    private BufferedReader br;
    private PrintWriter pw;
    private BufferedReader reader_terminal;

    public Client1(Socket s, BufferedReader br, PrintWriter pw, BufferedReader reader_terminal) throws IOException {
        this.s = s;
        this.br = br;
        this.pw = pw;
        this.reader_terminal = reader_terminal;
    }

    public void run(){
        String line;
        try {
            while(true) {

                line = reader_terminal.readLine();

                if (line == null || line.equals("sair"))
                    break;
                pw.println(line);
                pw.flush();

                String answer = br.readLine();
                System.out.println(answer);

                String[] parts_line = line.split(" ");
                String[] parts_answer = answer.split(" ");

                if (answer.matches("[0-9]+")) {
                    FileOperations.sendFile(s, parts_line[5]);
                } else if (parts_answer[0].equals("ready")) {
                    FileOperations.saveFile(s, Integer.parseInt(parts_answer[1]), parts_answer[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}