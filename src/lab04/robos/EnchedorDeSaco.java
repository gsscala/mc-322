// Declaração do pacote ao qual esta interface pertence
package robos;

// Importação da interface necessária para comunicação
import comunicacao.Comunicavel;

/**
 * Interface que define o comportamento de um robô "chato" que pode enviar múltiplas mensagens repetidas.
 * Esta interface é implementada por robôs que têm capacidade de enviar spam para outros robôs.
 */
public interface EnchedorDeSaco {
    
    /**
     * Método que realiza o envio repetido de mensagens a um robô específico.
     * 
     * @param roboASerSpammado Robô alvo que receberá as mensagens repetidas
     * @param numeroDeVezes Quantidade de vezes que a mensagem será enviada
     * @throws RoboDesligadoException Se o robô alvo estiver desligado durante a tentativa de comunicação
     */
    void encherOSaco(Comunicavel roboASerSpammado, int numeroDeVezes) throws RoboDesligadoException;
}