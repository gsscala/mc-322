// Declaração do pacote ao qual esta classe pertence
package robos;

// Importação de exceção necessária para comunicação
import comunicacao.ErroComunicacaoException;

/**
 * Classe que representa um robô aéreo, especializado em operações em altitude.
 * Estende a classe Robo para adicionar funcionalidades de voo.
 */
public class RoboAereo extends Robo {
    // Altitude atual do robô (em unidades do ambiente)
    private int altitude = 0;
    
    // Altitude máxima permitida para este robô (0 até altitudeMaxima)
    private int altitudeMaxima;

    /**
     * Construtor que inicializa o robô aéreo com parâmetros específicos.
     * 
     * @param nome Identificação do robô
     * @param xIni Posição inicial no eixo X
     * @param yIni Posição inicial no eixo Y
     * @param direcao Direção inicial
     * @param altitudeMaxima Altitude máxima permitida
     */
    public RoboAereo(String nome, int xIni, int yIni, String direcao, int altitudeMaxima) {
        // Chama o construtor da superclasse Robo
        super(nome, xIni, yIni, direcao);
        // Define a altitude máxima para o robô
        setAltitudeMaxima(altitudeMaxima);
        setDescricao("Um robô aéreo é um veículo aéreo não tripulado, operado remotamente ou de forma autônoma, empregado para tarefas com ambiente.");
    }

    /**
     * Sobrescrita do método da superclasse para retornar a altitude atual.
     * @return Altitude atual do robô
     */
    @Override
    public int getAltitude() {
        return altitude;
    }

    /**
     * Define a altitude atual do robô.
     * @param altitude Nova altitude
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    /**
     * Obtém a altitude máxima permitida para este robô.
     * @return Altitude máxima
     */
    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    /**
     * Define a altitude máxima permitida para este robô.
     * @param altitudeMaxima Nova altitude máxima
     */
    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }

    /**
     * Aumenta a altitude do robô.
     * @param deltaZ Quantidade a subir (deve ser positiva)
     */
    public void subir(int deltaZ) {
        // Valida se o valor é positivo
        if (deltaZ < 0) {
            System.out.println("O valor não pode ser negativo!");
            return;
        }
    
        // Calcula nova altitude
        int novaAltitude = getAltitude() + deltaZ;
        
        // Verifica se excede a altitude máxima
        if (novaAltitude >= getAltitudeMaxima()) {
            novaAltitude = getAltitudeMaxima() - 1;  // Ajusta para o máximo permitido
            System.out.println("Altitude máxima atingida!");
        }
        
        // Atualiza o mapa do ambiente ANTES de alterar a altitude
        getAmbiente().moverRoboMapa(
            getPosicaoX(), getPosicaoY(), getAltitude(), 
            getPosicaoX(), getPosicaoY(), novaAltitude
        );
        
        // Atualiza a altitude do robô
        setAltitude(novaAltitude);
    }
    
    /**
     * Diminui a altitude do robô.
     * @param deltaZ Quantidade a descer (deve ser positiva)
     */
    public void descer(int deltaZ) {
        // Valida se o valor é positivo
        if (deltaZ < 0) {
            System.out.println("O valor não pode ser negativo!");
            return;
        }
    
        // Calcula nova altitude
        int novaAltitude = getAltitude() - deltaZ;
        
        // Verifica se está abaixo do mínimo permitido
        if (novaAltitude < 0) {
            novaAltitude = 0;  // Define altitude mínima
            System.out.println("Altitude mínima atingida!");
        }
        
        // Atualiza a altitude do robô
        setAltitude(novaAltitude);
    }

    /**
     * Executa tarefas específicas para robôs aéreos.
     * Adiciona comandos de subir e descer além das tarefas básicas.
     * 
     * @param tarefa Nome da tarefa a executar
     * @param args Argumentos adicionais para a tarefa
     * @throws RoboDesligadoException Se robô estiver desligado
     * @throws ErroComunicacaoException Em falhas de comunicação
     * @throws TaskNotFoundException Se tarefa não for reconhecida
     */
    @Override
    public void executarTarefa(String tarefa, String[] args) 
        throws RoboDesligadoException, ErroComunicacaoException, TaskNotFoundException {
        
        // Verifica estado antes de executar
        if (getEstado() != EstadoRobo.LIGADO) {
            throw new RoboDesligadoException("Robô desligado não pode executar tarefas");
        }
        
        // Seleciona tarefa baseada no comando
        try{
            switch (tarefa) {
                case "subir":
                    // Converte primeiro argumento para inteiro e executa subida
                    subir(Integer.parseInt(args[0]));
                    break;
                case "descer":
                    // Converte primeiro argumento para inteiro e executa descida
                    descer(Integer.parseInt(args[0]));
                    break;
                case "roubar":
                    // Executa roubo de bateria (herdado da superclasse)
                    roubar();
                    break;
                default:
                    // Lança exceção para tarefas não implementadas
                    throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Número de argumentos insuficiente. Execute help para descobrir como rodar o comando corretamente!");
        }
    }
}