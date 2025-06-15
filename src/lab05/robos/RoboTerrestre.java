// Declaração do pacote ao qual esta classe pertence
package robos;

import java.util.ArrayList;

// Importações necessárias para interfaces e exceções
import comunicacao.Comunicavel;
import comunicacao.ErroComunicacaoException;
import sensores.Sensor;
import sensores.SensorProximidade;
import sensores.SensorUmidade;
import sensores.Sensoreavel;
import utils.RandomNumberGenerator;

/**
 * Classe que representa um robô terrestre especializado.
 * Estende a classe Robo básica e implementa capacidade de comunicação.
 */
public class RoboTerrestre extends Robo implements Comunicavel, Sensoreavel {
    // Velocidade máxima do robô (em unidades do ambiente por movimento)
    private int velocidadeMaxima;
    private ArrayList<Sensor>sensores;

    
    /**
     * Construtor que inicializa o robô terrestre com parâmetros específicos.
     * 
     * @param nome Identificação do robô
     * @param xIni Posição inicial no eixo X
     * @param yIni Posição inicial no eixo Y
     * @param direcao Direção inicial (Norte, Sul, Leste, Oeste)
     * @param velocidadeMaxima Velocidade máxima permitida (deve ser >= 1)
     */
    public RoboTerrestre(String nome, int xIni, int yIni, String direcao, int velocidadeMaxima) {
        // Chama o construtor da superclasse Robo
        super(nome, xIni, yIni, direcao);
        
        // Valida e define a velocidade máxima
        if (velocidadeMaxima < 1) {
            // Define valor mínimo seguro
            setVelocidadeMaxima(1);
            // Notifica sobre ajuste necessário
            System.out.println("Velocidade máxima não pode ser menor do que 1");
        } else {
            // Usa o valor fornecido
            setVelocidadeMaxima(velocidadeMaxima);
        }

        setSensores(SensorProximidade(new RandomNumberGenerator(1, 8).generate()));

        setSensores(SensorUmidade(new RandomNumberGenerator(1, 8).generate()));

        setDescricao("Um robô terrestre opera e se desloca sobre a superfície do solo e pode superar obstaculos no seu caminho.");
    }

    
    public Arraylist<Sensor> getSensores (){
        return sensores;
    }

    public setSensores(Sensor sensor){
        sensores.add(sensor);
    }

    /**
     * Obtém a velocidade máxima configurada para o robô.
     * @return Velocidade máxima em unidades de movimento
     */
    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    /**
     * Define a velocidade máxima do robô.
     * @param velocidadeMaxima Novo valor de velocidade máxima (deve ser >= 1)
     */
    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    /**
     * Ativa todos os sensores do robô para monitoramento.
     * 
     * @throws RoboDesligadoException Se o robô estiver desligado
     */
    public void acionarSensores() throws RoboDesligadoException {
        // Verifica se o robô está ligado
        if (getEstado() == EstadoRobo.LIGADO) {
            // Ativa cada sensor individualmente
            for (Sensor sensor : this.getSensores()) {
                sensor.monitorar();
            }
        } else {
            throw new RoboDesligadoException("Robô desligado não consegue usar sensores");
        }
    }

    /**
     * Move o robô considerando uma velocidade específica.
     * Implementa lógica de "turbo" quando a velocidade é alta o suficiente.
     * 
     * @param deltaX Variação no eixo X
     * @param deltaY Variação no eixo Y
     * @param velocidade Velocidade do movimento
     */
    public void mover(int deltaX, int deltaY, int velocidade) {
        // Verifica se a velocidade excede o máximo permitido
        if (velocidade > getVelocidadeMaxima()) {
            System.out.println("Não é permitido exceder a velocidade máxima: " + getVelocidadeMaxima());
            return;
        }
    
        // Calcula distância total a percorrer
        int distancia = Math.abs(deltaX) + Math.abs(deltaY);
    
        // Verifica condições para ativar modo turbo (ignora obstáculos)
        if (distancia > 0 && (velocidade / distancia) > 2) {
            // Calcula nova posição
            int novaPosX = getPosicaoX() + deltaX;
            int novaPosY = getPosicaoY() + deltaY;
    
            // Garante que a nova posição está dentro dos limites do ambiente
            novaPosX = Math.max(0, Math.min(novaPosX, getAmbiente().getLargura() - 1));
            novaPosY = Math.max(0, Math.min(novaPosY, getAmbiente().getProfundidade() - 1));

            // Atualiza o mapa ANTES de alterar estado interno
            getAmbiente().moverRoboMapa(getPosicaoX(), getPosicaoY(), getAltitude(), novaPosX, novaPosY, getAltitude());

            // Atualiza posição do robô
            setPosicaoX(novaPosX);
            setPosicaoY(novaPosY);
    
            // Feedback do movimento turbo
            System.out.println(getNome() + " usou TURBO para mover diretamente para (" + novaPosX + ", " + novaPosY + ")");
        } else {
            // Movimento normal (respeita obstáculos)
            super.mover(deltaX, deltaY);
        }
    }
    
    /**
     * Implementação da interface Comunicavel: envia mensagem para outro robô.
     * 
     * @param destinatario Robô que receberá a mensagem
     * @param mensagem Conteúdo da mensagem
     * @throws RoboDesligadoException Se o robô remetente estiver desligado
     */
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException {
        // Verifica estado do robô antes de enviar
        if (getEstado() == EstadoRobo.LIGADO) {
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
    @Override
    public void receberMensagem(String mensagem) throws RoboDesligadoException {
        // Verifica estado antes de receber
        if (getEstado() == EstadoRobo.LIGADO) {
            // Exibe a mensagem recebida
            System.out.println(getNome() + " recebeu a mensagem: " + mensagem);
        } else {
            throw new RoboDesligadoException("Destinatario desligado, não é possível receber mensagem.");
        }   
    }

    /**
     * Executa tarefas específicas para robôs terrestres.
     * 
     * @param tarefa Nome da tarefa a executar
     * @param args Argumentos adicionais para a tarefa
     */
    @Override
    public void executarTarefa(String tarefa, String[] args) 
        throws RoboDesligadoException, ErroComunicacaoException, TaskNotFoundException {
        
        // Seleciona ação baseada no nome da tarefa
        switch (tarefa) {
            case "roubar":
                roubar();  // Executa roubo de bateria (herdado)
                break;
            default:
                // Tarefa não reconhecida
                throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);
        }
    }
}