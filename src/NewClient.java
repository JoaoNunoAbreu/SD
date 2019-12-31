import Exceptions.NomeJaExisteException;

public class NewClient{

    public static void main(String[] args) throws Exception, NomeJaExisteException {
        SoundCloudRemoto br = new SoundCloudRemoto("localhost",12345);

        System.out.println(br.registarUser("ola","123"));

        System.out.println(br.login("ola","123"));
    }
}
