import java.io.*;
import java.net.Socket;

public class Client{

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",12345);

        BufferedReader reader_terminal = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream());

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
            String[] parts_answer = line.split(" ");

            if(answer.matches("[0-9]+")){
                FileOperations.sendFile(s,parts_line[5]);
            }
            else if(parts_answer[0].equals("ready")){
                System.out.println("ENTROU");
                FileOperations.saveFile(s,Integer.parseInt(parts_answer[1]),parts_answer[2]);
            }
        }
        s.shutdownOutput();
        s.shutdownInput();
        s.close();
    }
}