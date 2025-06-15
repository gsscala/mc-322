// Declaração do pacote ao qual esta classe pertence
package obstaculos;

// Importações necessárias para a classe
import robos.EstadoRobo;      // Enumeração de estados do robô
import robos.Robo;            // Classe que representa um robô
import ambiente.Ambiente;     // Classe que representa o ambiente
import entity.*;              // Classes e interfaces de entidades

/**
 * Classe que representa um obstáculo no ambiente.
 * Implementa a interface Entidade para integração com o sistema.
 */
public class Obstaculo implements Entidade {
    // Coordenadas da área do obstáculo (formato retangular)
    private int posicaoX1;  // Coordenada X do ponto superior esquerdo
    private int posicaoY1;  // Coordenada Y do ponto superior esquerdo
    private int posicaoX2;  // Coordenada X do ponto inferior direito
    private int posicaoY2;  // Coordenada Y do ponto inferior direito
    
    // Tipo do obstáculo (define comportamento e características)
    private TipoObstaculo tipoObstaculo;
    
    // Tipo fixo de entidade (sempre OBSTACULO)
    private TipoEntidade tipoEntidade = TipoEntidade.OBSTACULO;
    
    // Representação visual fixa do obstáculo
    private char representacao = 'X';

    // Referência ao ambiente onde o obstáculo está inserido
    private Ambiente ambiente;

    /**
     * Construtor que cria um obstáculo com área definida e tipo específico.
     * @param posicaoX1 Coordenada X do ponto inicial (superior esquerdo)
     * @param posicaoY1 Coordenada Y do ponto inicial (superior esquerdo)
     * @param posicaoX2 Coordenada X do ponto final (inferior direito)
     * @param posicaoY2 Coordenada Y do ponto final (inferior direito)
     * @param tipoObstaculo Tipo do obstáculo que define suas características
     */
    public Obstaculo(int posicaoX1, int posicaoY1, int posicaoX2, int posicaoY2, TipoObstaculo tipoObstaculo) {
        // Inicializa as coordenadas do obstáculo
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        
        // Define o tipo do obstáculo
        this.tipoObstaculo = tipoObstaculo;
    }

    // --- Métodos de acesso (Getters) ---
    
    /**
     * Obtém a coordenada X do ponto superior esquerdo
     * @return Valor inteiro da coordenada X1
     */
    public int getPosicaoX1() {
        return posicaoX1;
    }

    /**
     * Obtém a coordenada Y do ponto superior esquerdo
     * @return Valor inteiro da coordenada Y1
     */
    public int getPosicaoY1() {
        return posicaoY1;
    }

    /**
     * Obtém a coordenada X do ponto inferior direito
     * @return Valor inteiro da coordenada X2
     */
    public int getPosicaoX2() {
        return posicaoX2;
    }

    /**
     * Obtém a coordenada Y do ponto inferior direito
     * @return Valor inteiro da coordenada Y2
     */
    public int getPosicaoY2() {
        return posicaoY2;
    }

    /**
     * Implementação da interface Entidade: obtém a posição central X
     * @return Coordenada X do centro do obstáculo
     */
    @Override
    public int getPosicaoX() {
        // Calcula a posição central como média entre X1 e X2
        return (posicaoX1 + posicaoX2) / 2;
    }

    /**
     * Implementação da interface Entidade: obtém a posição central Y
     * @return Coordenada Y do centro do obstáculo
     */
    @Override
    public int getPosicaoY() {
        // Calcula a posição central como média entre Y1 e Y2
        return (posicaoY1 + posicaoY2) / 2;
    }
    
    /**
     * Obtém o tipo específico do obstáculo
     * @return Instância de TipoObstaculo
     */
    public TipoObstaculo getTipoObstaculo() {
        return tipoObstaculo;
    }

    /**
     * Implementação da interface Entidade: obtém o tipo genérico
     * @return Tipo de entidade (sempre OBSTACULO)
     */
    @Override
    public TipoEntidade getTipo() {
        return tipoEntidade;
    }

    /**
     * Implementação da interface Entidade: obtém descrição do obstáculo
     * @return Descrição baseada no tipo de obstáculo
     */
    @Override
    public String getDescricao() {
        return tipoObstaculo.toString();
    }

    /**
     * Implementação da interface Entidade: obtém representação visual
     * @return Caractere 'X' representando o obstáculo
     */
    @Override
    public char getRepresentacao() {
        return representacao;
    }

    // --- Métodos de modificação (Setters) ---
    
    public void setPosicaoX1(int posicaoX1) {
        this.posicaoX1 = posicaoX1;
    }

    public void setPosicaoY1(int posicaoY1) {
        this.posicaoY1 = posicaoY1;
    }

    public void setPosicaoX2(int posicaoX2) {
        this.posicaoX2 = posicaoX2;
    }

    public void setPosicaoY2(int posicaoY2) {
        this.posicaoY2 = posicaoY2;
    }

    public void setTipoObstaculo(TipoObstaculo tipo) {
        this.tipoObstaculo = tipo;
    }

    // --- Métodos delegados ao TipoObstaculo ---
    
    /**
     * Verifica se este obstáculo bloqueia a passagem
     * @return true se bloqueia a passagem, false caso contrário
     */
    public boolean isBloqueiaPassagem() {
        return tipoObstaculo.isBloqueiaPassagem();
    }

    /**
     * Obtém a altura do obstáculo
     * @return Altura em unidades do ambiente
     */
    public int getAltura() {
        return tipoObstaculo.getAltura();
    }

    /**
     * Verifica se este obstáculo é letal
     * @return true se for letal, false caso contrário
     */
    public boolean isLetal() {
        return tipoObstaculo.isLetal();
    }

    /**
     * Obtém o nível de umidade associado ao obstáculo
     * @return Valor inteiro representando a umidade
     */
    public int getNivelUmidade() {
        return tipoObstaculo.getNivelUmidade();
    }

    /**
     * Implementação da interface Entidade: associa o obstáculo a um ambiente
     * @param ambiente Ambiente onde o obstáculo será inserido
     */
    @Override
    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
        
        // Percorre toda a área ocupada pelo obstáculo
        for (int x = getPosicaoX1(); x <= getPosicaoX2(); x++) {
            for (int y = getPosicaoY1(); y <= getPosicaoY2(); y++) {
                // Percorre todas as alturas do obstáculo
                for (int z = 0; z <= getAltura(); z++) {
                    // Atualiza o mapa do ambiente marcando esta posição como obstáculo
                    ambiente.getMapa()[x][y][z] = tipoEntidade;
                }
            }
        }
    }

    /**
     * Obtém o ambiente associado a este obstáculo
     * @return Instância do Ambiente
     */
    public Ambiente getAmbiente() {
        return ambiente;
    }

    /**
     * Verifica se uma posição específica está dentro dos limites do obstáculo
     * @param x Coordenada X a verificar
     * @param y Coordenada Y a verificar
     * @param altura Altura a verificar
     * @return true se está dentro dos limites, false caso contrário
     */
    public boolean dentroDosLimites(int x, int y, int altura) {
        // Verifica se (x,y) está dentro da área retangular do obstáculo
        // e se a altura está abaixo da altura do obstáculo
        return x >= posicaoX1 && x <= posicaoX2 && 
               y >= posicaoY1 && y <= posicaoY2 && 
               altura <= getAltura();
    }   

    /**
     * Trata uma colisão entre um robô e este obstáculo
     * @param robo Robô que colidiu com o obstáculo
     * @param x Posição X da colisão
     * @param y Posição Y da colisão
     * @param altura Altura da colisão
     * @throws ColisaoException Exceção lançada dependendo do tipo de colisão
     */
    public void handleColisao(Robo robo, int x, int y, int altura) throws ColisaoException {
        // Se não há colisão efetiva, sai do método
        if (!dentroDosLimites(x, y, altura)) {
            return;
        } 
        
        // Tratamento para obstáculos que bloqueiam passagem
        if (isBloqueiaPassagem()) {
           throw new ColisaoException("Colisão com obstáculo: " + getDescricao());
        }

        // Tratamento para obstáculos letais
        if (isLetal()) {
            // Marca o robô como morto
            robo.setEstado(EstadoRobo.MORTO);
            throw new ColisaoException("Colisão letal com obstáculo: " + getDescricao());
        }

        // Tratamento para obstáculos com umidade (ex: poças d'água)
        if (getNivelUmidade() > 0) {
            // Reduz a bateria do robô proporcionalmente à umidade
            robo.setBateria(robo.getBateria() - 2 * getNivelUmidade());

            System.out.println("Você pisou em uma poça e se molhou!");

            // Verifica se a bateria acabou
            if (robo.getBateria() <= 0) {
                robo.setEstado(EstadoRobo.MORTO);
                throw new ColisaoException("Robô morreu por falta de bateria após pisar em uma poça: " + getDescricao());
            }
        }
    }
}