import java.io.*;
import java.net.Socket;

public class Client{

    private static int MAXSIZE = 524288; // 0.5 Mb = 500 Kb

    public static void sendFile(Socket s, String file) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[MAXSIZE];

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }
        fis.close();
        dos.close();
    }

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",12345);

        BufferedReader reader_terminal = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream());

        String line;
        while(true){
            line = reader_terminal.readLine();
            if(line == null)
                break;
            pw.println(line);
            pw.flush();

            String answer = br.readLine();
            System.out.println(answer);
            if(answer.equals("Upload permitido")){
                sendFile(s,"musicas/AlanWalker_Faded.mp3");
            }
        }
        s.shutdownOutput();
        s.shutdownInput();
        s.close();
    }
}