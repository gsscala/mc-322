package comunicacao;

import java.util.ArrayList;

public class CentralComunicacao {
    private ArrayList<String> mensagens = new ArrayList<>();

    public void registrarMensagem(String remetente, String destinatario, String mensagem) {
        String msg = "De: " + remetente + "\n" + " Para: " + destinatario + "\n" + " Mensagem: " + mensagem;
        mensagens.add(msg);
    }

    public void exibirMensagens() {
        for (String msg : mensagens) {
            System.out.println(msg);
        }
    }
}
