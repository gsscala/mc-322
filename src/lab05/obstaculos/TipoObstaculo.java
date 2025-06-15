// Declaração do pacote ao qual este enum pertence
package obstaculos;

/**
 * Enumeração que define os tipos de obstáculos existentes no ambiente.
 * Cada tipo possui características específicas que determinam seu comportamento.
 */
public enum TipoObstaculo {
    // Constantes do enum com seus respectivos parâmetros:
    
    /**
     * Representa uma poça d'água no ambiente.
     * Características:
     *   - Altura: 0 (não possui altura)
     *   - Não bloqueia passagem
     *   - Não é letal
     *   - Nível de umidade: 80 (alto)
     */
    POCA(0, false, false, 80),    
    
    /**
     * Representa uma pedra no ambiente.
     * Características:
     *   - Altura: 5 unidades
     *   - Bloqueia passagem
     *   - Não é letal
     *   - Nível de umidade: 5 (baixo)
     */
    PEDRA(5, true, false, 5),      
    
    /**
     * Representa um prédio no ambiente.
     * Características:
     *   - Altura: 15 unidades (alto)
     *   - Bloqueia passagem
     *   - Não é letal
     *   - Nível de umidade: 1 (muito baixo)
     */
    PREDIO(15, true, false, 1),    
    
    /**
     * Representa um cacto no ambiente.
     * Características:
     *   - Altura: 3 unidades
     *   - Bloqueia passagem
     *   - É letal (pode destruir robôs)
     *   - Nível de umidade: 10 (moderado)
     */
    CACTO(3, true, true, 10);      

    // Campos finais que armazenam as características dos obstáculos:
    private final int altura;                // Altura do obstáculo em unidades do ambiente
    private final boolean bloqueiaPassagem;  // Indica se o obstáculo bloqueia a passagem de robôs
    private final boolean letal;             // Indica se o obstáculo é capaz de destruir robôs
    private final int nivelUmidade;          // Nível de umidade associado ao obstáculo

    /**
     * Construtor do enum que inicializa as características de cada tipo de obstáculo.
     * 
     * @param altura Altura em unidades do ambiente
     * @param bloqueiaPassagem True se impede a passagem de robôs
     * @param letal True se pode destruir robôs
     * @param nivelUmidade Quantidade de umidade associada
     */
    TipoObstaculo(int altura, boolean bloqueiaPassagem, boolean letal, int nivelUmidade) {
        // Inicialização dos campos com os valores passados como parâmetros
        this.altura = altura;
        this.bloqueiaPassagem = bloqueiaPassagem;
        this.letal = letal;
        this.nivelUmidade = nivelUmidade;
    }

    // --- Métodos de acesso (Getters) ---
    
    /**
     * Obtém a altura do tipo de obstáculo.
     * @return Altura em unidades do ambiente
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Verifica se este tipo de obstáculo bloqueia a passagem de robôs.
     * @return True se bloqueia a passagem, False caso contrário
     */
    public boolean isBloqueiaPassagem() {
        return bloqueiaPassagem;
    }

    /**
     * Verifica se este tipo de obstáculo é letal para robôs.
     * @return True se pode destruir robôs, False caso contrário
     */
    public boolean isLetal() {
        return letal;
    }

    /**
     * Obtém o nível de umidade associado a este tipo de obstáculo.
     * @return Valor inteiro representando a umidade
     */
    public int getNivelUmidade() {
        return nivelUmidade;
    }
}