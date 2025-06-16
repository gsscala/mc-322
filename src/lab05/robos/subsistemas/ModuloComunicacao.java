package robos.subsistemas;

import comunicacao.Comunicavel;
import robos.EstadoRobo;
import robos.Robo;
import robos.RoboDesligadoException;

public class ModuloComunicacao {

    Robo robo;

    public ModuloComunicacao(Robo robo) {
        this.robo = robo;
    }

    public Robo getRobo() {
        return robo;
    }

    /**
     * Implementação da interface Comunicavel: envia mensagem para outro robô.
     * 
     * @param destinatario Robô que receberá a mensagem
     * @param mensagem Conteúdo da mensagem
     * @throws RoboDesligadoException Se o robô remetente estiver desligado
     */
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        // Verifica estado do robô antes de enviar
        if (getRobo().getEstado() == EstadoRobo.LIGADO) {
            try {
                // Tenta entregar a mensagem ao destinatário
                destinatario.receberMensagem(mensagem);
            } catch (RoboDesligadoException e) {
                // Trata erro se destinatário estiver desligado
                System.out.println("Erro ao enviar mensagem: " + e.getMessage());
            }
        } else {
            throw new RoboDesligadoException("Remetente desligado, não é possível enviar mensagem.");
        }
    }       

    /**
     * Implementação da interface Comunicavel: recebe uma mensagem.
     * 
     * @param mensagem Conteúdo da mensagem recebida
     * @throws RoboDesligadoException Se o robô destinatário estiver desligado
     */
    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        // Verifica estado antes de receber
        if (getRobo().getEstado() == EstadoRobo.LIGADO) {
            // Exibe a mensagem recebida
            System.out.println(getRobo().getNome() + " recebeu a mensagem: " + mensagem);
        } else {
            throw new RoboDesligadoException("Destinatario desligado, não é possível receber mensagem.");
        }   
    }

}
