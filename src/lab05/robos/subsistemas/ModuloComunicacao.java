package robos.subsistemas;

import comunicacao.Comunicavel;
import robos.EstadoRobo;
import robos.RoboDesligadoException;

public class ModuloComunicacao {

    public ModuloComunicacao(Comunicave) {

    }

        /**
     * Implementação da interface Comunicavel: recebe uma mensagem.
     * 
     * @param mensagem Conteúdo da mensagem recebida
     * @throws RoboDesligadoException Se o robô destinatário estiver desligado
     */
    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        // Verifica se o robô está ligado
        if (getEstado() == EstadoRobo.LIGADO) {
            // Exibe a mensagem recebida
            System.out.println(getNome() + " recebeu a mensagem: " + mensagem);
        } else {
            throw new RoboDesligadoException("Destinatario desligado, não é possível receber mensagem.");
        }
    }
}
