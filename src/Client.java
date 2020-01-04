import java.io.*;
import java.net.Socket;

public class Client{

    private static Socket s;
    private static BufferedReader br;

    public static void main(String[] args) throws Exception {

        s = new Socket("localhost",12345);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader reader_terminal = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

        new Thread(new ClientThread()).start();

        String line;
        while(true){
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
            }
            else if(parts_answer[0].equals("ready")){
                FileOperations.saveFile(s,Integer.parseInt(parts_answer[1]),parts_answer[2]);
            }
            ClientThread.setActive(false);
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
                BufferedReader bre = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while (true) {
                    String answer = bre.readLine();
                    System.out.println("Da Thread : " + answer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}