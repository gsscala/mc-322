// Declaração do pacote ao qual esta classe pertence
package robos;

// Importação de classes necessárias
import java.util.ArrayList;  // Para uso de listas dinâmicas
import ambiente.Ambiente;    // Classe que representa o ambiente
import ambiente.ForaMapaException;  // Exceção para posições fora do mapa
import sensores.Sensor;      // Interface para sensores
import utils.DistanceCalculator;  // Utilitário para cálculo de distância
import entity.*;             // Entidades do sistema
import obstaculos.ColisaoException;  // Exceção para colisões
import comunicacao.ErroComunicacaoException;  // Exceção para erros de comunicação

/**
 * Classe que representa um robô genérico no sistema.
 * Implementa as interfaces Entidade e Ladrao para integração com o ambiente e capacidade de roubo.
 */
public class Robo implements Entidade, Ladrao {
    // Atributos privados que definem o estado do robô
    private String nome;          // Nome identificador do robô
    private int posicaoX;         // Posição atual no eixo X (horizontal)
    private int posicaoY;         // Posição atual no eixo Y (vertical)
    private String direcao;       // Direção atual do robô (Norte, Sul, Leste, Oeste)
    private EstadoRobo estado = EstadoRobo.LIGADO;  // Estado operacional (inicia ligado)
    private String descricao = "Robô genérico";  // Descrição básica do robô
    private TipoEntidade tipo = TipoEntidade.ROBO;  // Tipo fixo de entidade
    private char representacao = '0';  // Caractere de representação visual
    private int bateria = 2000;   // Nível de bateria inicial
    private Ambiente ambiente;    // Ambiente onde o robô está inserido
    protected ArrayList<Sensor> sensores = new ArrayList<>();  // Lista de sensores acoplados

    /**
     * Construtor que inicializa o robô com parâmetros básicos.
     * 
     * @param nome Identificação do robô
     * @param xIni Posição inicial no eixo X
     * @param yIni Posição inicial no eixo Y
     * @param direcao Direção inicial
     */
    public Robo(String nome, int xIni, int yIni, String direcao) {
        setNome(nome);           // Define o nome
        setPosicaoX(xIni);       // Define posição X inicial
        setPosicaoY(yIni);       // Define posição Y inicial
        setDirecao(direcao);     // Define direção inicial
    }

    /**
     * Obtém o ambiente associado ao robô.
     * @return Instância do ambiente atual
     */
    public Ambiente getAmbiente() {
        return ambiente;
    }

    /**
     * Associa o robô a um ambiente específico.
     * Atualiza o mapa do ambiente com a posição inicial do robô.
     * 
     * @param ambiente Ambiente a ser associado
     */
    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
        // Atualiza o mapa na posição inicial do robô
        ambiente.getMapa()[getPosicaoX()][getPosicaoY()][getAltitude()] = TipoEntidade.ROBO;
    }

    /**
     * Adiciona um sensor ao robô, evitando duplicatas por tipo.
     * 
     * @param sensor Sensor a ser adicionado
     */
    public void addSensor(Sensor sensor) {
        // Verifica se já existe sensor do mesmo tipo
        for (Sensor s : sensores) {
            if (s.getClass().equals(sensor.getClass())) {
                System.out.println("Já existe um sensor do tipo " + sensor.getClass().getSimpleName() + " no robô.");
                return; 
            }
        }
        // Adiciona o sensor e estabelece associação bidirecional
        sensores.add(sensor);
        sensor.setRobo(this);
    }

    /**
     * Liga o robô, alterando seu estado para LIGADO.
     */
    public void ligar() {
        estado = EstadoRobo.LIGADO;
    }
    
    /**
     * Desliga o robô, alterando seu estado para DESLIGADO.
     */
    public void desligar() {
        estado = EstadoRobo.DESLIGADO;
    }
    
    /**
     * Obtém a lista de sensores do robô.
     * @return Lista de sensores
     */
    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    // --- Métodos de acesso (Getters) ---
    public String getDirecao() {
        return direcao;
    }

    // --- Métodos de modificação (Setters) ---
    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public EstadoRobo getEstado() {
        return estado;  
    }  

    /**
     * Altera o estado do robô e retorna mensagem descritiva.
     * 
     * @param estado Novo estado do robô
     * @return Mensagem de confirmação
     */
    public String setEstado(EstadoRobo estado) {
        this.estado = estado;
        return String.format("Robô %s está agora %s.", getNome(), 
            estado == EstadoRobo.DESLIGADO ? "desligado" : 
            estado == EstadoRobo.LIGADO ? "ligado" : "morto");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;
    }

    public int getBateria() {
        return bateria;  
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;  
    }

    @Override
    public int getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;
    }

    @Override
    public int getPosicaoY() {
        return posicaoY;
    }

    /**
     * Obtém a altitude do robô (fixa em 0 para robôs terrestres).
     * @return Altitude atual (sempre 0)
     */
    public int getAltitude() {
        return 0;
    }

    @Override
    public String getDescricao() {
        return descricao;  
    }

    @Override
    public TipoEntidade getTipo() {
        return tipo;  
    }

    @Override
    public char getRepresentacao() {
        return representacao;  
    }

    /**
     * Move o robô em incrementos nos eixos X e Y, com tratamento de colisões e limites.
     * 
     * @param deltaX Variação no eixo X
     * @param deltaY Variação no eixo Y
     */
    public void mover(int deltaX, int deltaY) { 
        int x = getPosicaoX();  // Posição atual X
        int y = getPosicaoY();  // Posição atual Y

        // Determina direção do movimento (1 para positivo, -1 para negativo)
        int stepX = deltaX > 0 ? 1 : -1; 
        int stepY = deltaY > 0 ? 1 : -1;

        int xf = x;  // Posição final X
        int yf = y;  // Posição final Y

        // Movimento no eixo X (passo a passo)
        for (int dx = stepX; dx != deltaX + stepX; dx += stepX) {
            try {
                // Verifica se nova posição está dentro dos limites
                getAmbiente().dentroDosLimites(x + dx, yf, getAltitude());
            } catch (ForaMapaException e) {
                // Trata movimento fora do mapa: ajusta para limite mais próximo
                System.err.println("Erro ao mover no eixo X: " + e.getMessage());
                int end_pos = Math.max(0, Math.min(getAmbiente().getLargura()-1, x + dx));
                xf = end_pos;
                break;
            }

            try {
                // Verifica colisões na nova posição
                ambiente.handleColisoes(x + dx, y, getAltitude(), this);
            } catch (ColisaoException e) {
                // Trata colisão: retrocede um passo
                System.err.println("Erro ao mover no eixo X: " + e.getMessage());
                int end_pos = x + dx - stepX;
                xf = end_pos;
                break;
            }

            xf = x + dx;  // Atualiza posição X se movimento bem sucedido
        }

        // Movimento no eixo Y (passo a passo)
        for (int dy = stepY; dy != deltaY + stepY; dy += stepY) {
            try {
                // Verifica se nova posição está dentro dos limites
                getAmbiente().dentroDosLimites(xf, y + dy, getAltitude());
            } catch (ForaMapaException e) {
                // Trata movimento fora do mapa: ajusta para limite mais próximo
                System.err.println("Erro ao mover no eixo Y: " + e.getMessage());
                int end_pos = Math.max(0, Math.min(getAmbiente().getProfundidade()-1, y + dy));
                yf = end_pos;
                break;
            }
    
            try {
                // Verifica colisões na nova posição
                ambiente.handleColisoes(xf, y + dy, getAltitude(), this);
            } catch (ColisaoException e) {
                // Trata colisão: retrocede um passo
                System.err.println("Erro ao mover no eixo Y: " + e.getMessage());
                int end_pos = y + dy - stepY;
                yf = end_pos;
                break;
            }

            yf = y + dy;  // Atualiza posição Y se movimento bem sucedido
        }

        // Atualiza posições finais
        setPosicaoX(xf);
        setPosicaoY(yf);
        // Atualiza o mapa do ambiente
        getAmbiente().moverRoboMapa(x, y, getAltitude(), xf, yf, getAltitude());

        // Consome bateria proporcional à umidade do ambiente
        setBateria(getBateria() - getAmbiente().getUmidade());

        // Atualiza direção baseada no movimento predominante
        this.direcao = (deltaX != 0) ? (deltaX > 0 ? "Leste" : "Oeste") : 
                      (deltaY > 0 ? "Norte" : "Sul");
    }

    /**
     * Implementação da capacidade de roubo (interface Ladrao).
     * Rouba bateria de robôs próximos proporcional à distância.
     */
    @Override
    public void roubar() {
        for (Entidade entidade : this.getAmbiente().getEntidades()) {
            // Verifica se é outro robô e não ele mesmo
            if (entidade instanceof Robo && this != entidade) {
                Robo robo = (Robo) entidade;
                // Calcula distância entre os robôs
                int distance = (int) (new DistanceCalculator(this, robo).calculateDistance());
                // Calcula quantidade de bateria a roubar (inversamente proporcional à distância)
                int db = (int) (robo.getBateria() / distance);
                // Transfere bateria
                robo.setBateria(robo.getBateria() - db);
                this.setBateria(this.getBateria() + db);
                System.out.println(String.format(
                    "Roubou %d de bateria do robô %s, agora tem %d de bateria.", 
                    db, robo.getNome(), this.getBateria()));
            }
        }
    }

    /**
     * Executa uma tarefa específica com base em comando.
     * 
     * @param tarefa Nome da tarefa a ser executada
     * @param args Argumentos adicionais para a tarefa
     * @throws RoboDesligadoException Se o robô estiver desligado
     * @throws ErroComunicacaoException Em falhas de comunicação
     * @throws TaskNotFoundException Se a tarefa não for reconhecida
     */
    public void executarTarefa(String tarefa, String[] args) 
        throws RoboDesligadoException, ErroComunicacaoException, TaskNotFoundException {
        
        // Verifica estado antes de executar
        if (getEstado() != EstadoRobo.LIGADO) {
            throw new RoboDesligadoException("Robô desligado não pode executar tarefas");
        }
        
        switch (tarefa) {
            case "roubar":
                roubar();  // Executa roubo de bateria
                break;
            default:
                throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);
        }
    }
}