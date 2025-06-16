// Declaração do pacote ao qual esta interface pertence
package comunicacao;

// Importação da exceção personalizada necessária para a interface
import robos.RoboDesligadoException;
import robos.subsistemas.ModuloComunicacao;

// Definição da interface Comunicavel
public interface Comunicavel {

    ModuloComunicacao getModuloComunicacao(); // Require implementing classes to expose their module

    /**
     * Método para envio de mensagens a um destinatário
     * @param destinatario Entidade que receberá a mensagem (deve implementar Comunicavel)
     * @param mensagem Conteúdo textual da comunicação
     * @throws RoboDesligadoException Exceção lançada se o robô remetente estiver desligado
     */
    default void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        getModuloComunicacao().enviarMensagem(destinatario, mensagem);
    }

    /**
     * Método para recebimento de mensagens
     * @param mensagem Conteúdo textual recebido
     * @throws RoboDesligadoException Exceção lançada se o robô destinatário estiver desligado
     */
    default void receberMensagem(String mensagem) throws RoboDesligadoException {
        getModuloComunicacao().receberMensagem(mensagem);
    }
}