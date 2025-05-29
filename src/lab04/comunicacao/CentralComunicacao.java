// Declaração do pacote ao qual esta classe pertence
package comunicacao;

// Importação da classe ArrayList do Java Collections Framework
import java.util.ArrayList;

// Definição da classe que representa uma central de comunicação
public class CentralComunicacao {
    // Lista para armazenar mensagens registradas
    // Cada mensagem é uma String formatada contendo informações de comunicação
    private ArrayList<String> mensagens = new ArrayList<>();

    // Método para registrar uma nova mensagem na central
    // Parâmetros:
    //   remetente - entidade que está enviando a mensagem
    //   destinatario - entidade que deve receber a mensagem
    //   mensagem - conteúdo textual da comunicação
    public void registrarMensagem(String remetente, String destinatario, String mensagem) {
        // Formata a mensagem incluindo remetente, destinatário e conteúdo
        String msg = "De: " + remetente + "\n" + " Para: " + destinatario + "\n" + " Mensagem: " + mensagem;
        
        // Adiciona a mensagem formatada à lista de mensagens
        mensagens.add(msg);
    }

    // Método para exibir todas as mensagens registradas na central
    public void exibirMensagens() {
        // Itera sobre cada mensagem na lista usando for-each
        for (String msg : mensagens) {
            // Imprime a mensagem no console
            System.out.println(msg);
        }
    }
}