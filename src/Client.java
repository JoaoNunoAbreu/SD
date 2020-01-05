import java.io.*;
import java.net.Socket;

public class Client{

    private static Socket s;
    private static BufferedReader br;
    private static BufferedReader reader_terminal;
    private static PrintWriter pw;

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost",12345);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        reader_terminal = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

        String line;

        while(true){
            new Thread(new ClientThread()).start();
            line = reader_terminal.readLine();

            if(line == null || line.equals("sair"))
                break;
            pw.println(line);
            pw.flush();

            String answer = br.readLine();
            System.out.println(answer);

            String[] parts_line = line.split(" ");
            String[] parts_answer = answer.split(" ");

            if(answer.matches("[0-9]+")){
                FileOperations.sendFile(s,parts_line[5]);
                ClientThread.setActive(true);
            }
            else if(parts_answer[0].equals("ready")){
                FileOperations.saveFile(s,Integer.parseInt(parts_answer[1]),parts_answer[2]);
            }
        }
        s.shutdownOutput();
        s.shutdownInput();
        s.close();
    }
    public static class ClientThread implements Runnable {

        private static boolean active = false;

        public static void setActive(boolean active) {
            ClientThread.active = active;
        }

        public void run() {
            try {
                if(active) {
                    String answer = br.readLine();
                    System.out.println("Da Thread : " + answer);
                }
                active = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}