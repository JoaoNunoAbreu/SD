import java.util.ArrayList;
import java.util.List;

public class NewClient{

    public static void main(String[] args) throws Exception {

        SoundCloudRemoto br = new SoundCloudRemoto("localhost",12345);

        System.out.println(br.registarUser("ola","123"));

        System.out.println(br.login("ola","123"));

        // Upload

        List<String> etiquetas = new ArrayList<>();
        etiquetas.add("rock");
        etiquetas.add("pop");
        String path = "/Users/joaonunoabreu/Desktop/AlanWalker_Faded.mp3";
        System.out.println(br.upload("titulo","interprete",1999,etiquetas,path));

        // Download

        System.out.println(br.download(0,"/Users/joaonunoabreu/Desktop"));
    }
}
