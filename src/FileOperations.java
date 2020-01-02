import java.io.*;
import java.net.Socket;

public class FileOperations {

    private static int MAXSIZE = 524288; // 0.5 Mb = 500 Kb

    public static void sendFile(Socket s, String path) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(path);
        byte[] buffer = new byte[MAXSIZE];

        int read = 0;
        while ((read=fis.read(buffer)) > 0) {
            dos.write(buffer,0,read);
        }
        fis.close();
    }

    public static void saveFile(Socket s, int filesize, String path) throws IOException {
        DataInputStream dis = new DataInputStream(s.getInputStream());
        FileOutputStream fos = new FileOutputStream(path);
        byte[] buffer = new byte[MAXSIZE];

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
    }

    public static int calculaTam(String path){
        File file = new File(path);
        return (int) file.length();
    }
}
