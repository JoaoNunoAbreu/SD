import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker implements Runnable{

    private Socket s;
    private Banco b;

    public ServerWorker(Socket s, Banco b){
        this.s = s;
        this.b = b;
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
                    if (parts[0].equals("transferir")) {
                        b.transferir(Double.parseDouble(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                        answer = "Foi transferido " + parts[1] + " da conta " + parts[2] + " para a conta " + parts[3];
                    } else if (parts[0].equals("createAccount")) {
                        int res = b.createAccount(Double.parseDouble(parts[1]));
                        answer = Integer.toString(res);

                    } else if (parts[0].equals("closeAccount")) {
                        double res = b.closeAccount(Integer.parseInt(parts[1]));
                        answer = Double.toString(res);

                    } else if (parts[0].equals("totalBalance")) {
                        String[] accounts_str = parts[1].split(",");

                        int[] accounts_id = new int[accounts_str.length];
                        for (int i = 0; i < accounts_id.length; i++)
                            accounts_id[i] = Integer.parseInt(accounts_str[i]);

                        double res = b.totalBalance(accounts_id);
                        answer = Double.toString(res);

                    } else if (parts[0].equals("consultar")) {
                        double res = b.consulta(Integer.parseInt(parts[1]));
                        answer = Double.toString(res);

                    } else if (parts[0].equals("credito")) {
                        b.credito(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
                        b.addMovimento(Integer.parseInt(parts[1]), 0, line, b.consulta(Integer.parseInt(parts[1])));

                    } else if (parts[0].equals("debito")) {
                        b.debito(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
                        b.addMovimento(Integer.parseInt(parts[1]), 1, line, b.consulta(Integer.parseInt(parts[1])));

                    } else if (parts[0].equals("movimento")) {
                        List<Movimento> res = b.movimentos(Integer.parseInt(parts[1]));
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < res.size(); i++)
                            if(i == res.size()-1) sb.append(res.get(i));
                            else sb.append(res.get(i)).append(",");
                        answer = sb.toString();
                    }
                }
                catch(InvalidAccount | NotEnoughFunds e){
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
