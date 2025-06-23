// Declaração do pacote ao qual esta classe pertence
package robos;

// Importações necessárias para interfaces e exceções
import comunicacao.Comunicavel;
import comunicacao.ErroComunicacaoException;
import sensores.*;
import utils.RandomNumberGenerator;

import robos.subsistemas.ModuloComunicacao;

/**
 * Classe que representa um robô terrestre especializado.
 * Estende a classe Robo básica e implementa capacidade de comunicação.
 */
public class RoboTerrestre extends Robo implements Comunicavel, Sensoreavel {
    // Velocidade máxima do robô (em unidades do ambiente por movimento)
    private int velocidadeMaxima;

    private final ModuloComunicacao moduloComunicacao = new ModuloComunicacao(this);
    
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

        addSensor(new SensorProximidade(new RandomNumberGenerator(1, 8).generate()));


        setDescricao("Um robô terrestre opera e se desloca sobre a superfície do solo e pode superar obstaculos no seu caminho.");
    }

    public ModuloComunicacao getModuloComunicacao() {
        return moduloComunicacao;
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