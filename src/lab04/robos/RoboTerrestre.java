package robos;
import comunicacao.Comunicavel;
// A classe RoboTerrestre herda da classe Robo e representa um robô terrestre com uma velocidade máxima
import comunicacao.ErroComunicacaoException;

public class RoboTerrestre extends Robo implements Comunicavel {
    // Declaração da variável privada velocidadeMaxima, que armazena a velocidade máxima do robô terrestre      
    private int velocidadeMaxima;

    // Construtor da classe RoboTerrestre, que inicializa o robô com nome, posição (X e Y), direção e velocidade máxima
    public RoboTerrestre(String nome, int xIni, int yIni, String direcao, int velocidadeMaxima) {
        super(nome, xIni, yIni, direcao);  // Chama o construtor da classe pai (Robo) para inicializar nome, posição e direção
        // Verifica se a velocidade máxima é válida (não pode ser menor que 1)
        if (velocidadeMaxima < 1) {
            setVelocidadeMaxima(1);  // Define a velocidade mínima como 1
            System.out.println("Velocidade máxima não pode ser menor do que 1");  // Exibe um aviso
        } else {
            setVelocidadeMaxima(velocidadeMaxima);  // Define a velocidade máxima do robô terrestre
        }
    }

    // Método getter para obter a velocidade máxima do robô terrestre
    public int getVelocidadeMaxima() {
        return velocidadeMaxima;  // Retorna a velocidade máxima do robô
    }

    // Método setter para definir a velocidade máxima do robô terrestre
    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;  // Define a velocidade máxima do robô
    }

    // Método que permite ao robô terrestre mover-se, considerando a velocidade fornecida
    public void mover(int deltaX, int deltaY, int velocidade) {
        // Verifica se a velocidade é permitida
        if (velocidade > getVelocidadeMaxima()) {
            System.out.println("Não é permitido exceder a velocidade máxima: " + getVelocidadeMaxima());
            return;
        }
    
        // Distância total a percorrer (em passos)
        int distancia = Math.abs(deltaX) + Math.abs(deltaY);
    
        // Se a velocidade for muito alta pra distancia, ignora obstáculos
        if ((velocidade / distancia) > 2) {

            int novaPosX = getPosicaoX() + deltaX;
            int novaPosY = getPosicaoY() + deltaY;
    
            // Garante que não ultrapassa os limites do ambiente, robô tem essa noção
            novaPosX = Math.max(0, Math.min(novaPosX, getAmbiente().getLargura() - 1));
            novaPosY = Math.max(0, Math.min(novaPosY, getAmbiente().getProfundidade() - 1));

            getAmbiente().moverRoboMapa(getPosicaoX(), getPosicaoY(), getAltitude(), novaPosX, novaPosY, getAltitude()); // Vem antes do set

            setPosicaoX(novaPosX);
            setPosicaoY(novaPosY);
    
            System.out.println(getNome() + " usou TURBO para mover diretamente para (" + novaPosX + ", " + novaPosY + ")");
        }
        else {
            // Movimento normal, respeitando obstáculos
            super.mover(deltaX, deltaY);
        }
    }
    
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        // Verifica se o robô está ligado antes de enviar a mensagem
        
        if (getEstado() == EstadoRobo.LIGADO) {
            // System.out.println(getNome() + " enviou a mensagem: " + mensagem + " para " + destinatario.getNome()); isso eh na CLI    
            try {
                destinatario.receberMensagem(mensagem);  // Chama o método receberMensagem do destinatário
            } catch (RoboDesligadoException e) {
                System.out.println("Erro ao enviar mensagem: " + e.getMessage());  // Exibe mensagem de erro se o robô estiver desligado
            }

        } else {
            throw new RoboDesligadoException("Remetente desligado, não é possível enviar mensagem.");  // Lança exceção se o robô estiver desligado
        }
    }       

    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        // Verifica se o robô está ligado antes de receber a mensagem
        if (getEstado() == EstadoRobo.LIGADO) {
            System.out.println(getNome() + " recebeu a mensagem: " + mensagem);  // Exibe a mensagem recebida
        } else {
            throw new RoboDesligadoException("Destinatario desligado, não é possível receber mensagem.");  // Lança exceção se o robô estiver desligado
        }   
    }

    public void executarTarefa(String tarefa, String[] args) throws RoboDesligadoException, ErroComunicacaoException {
        switch (tarefa) {
            case "roubar":
                roubar();
                break;
            default:
                break;
        }
    }


}
