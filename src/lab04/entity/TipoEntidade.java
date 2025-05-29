// Declaração do pacote ao qual este enum pertence
package entity;

/**
 * Enumeração que define os tipos de entidades presentes no ambiente.
 * Cada tipo possui uma representação visual única em caractere.
 */
public enum TipoEntidade {
    /**
     * Representa uma posição vazia no ambiente
     * Representação visual: '_'
     */
    VAZIO('_'),
    
    /**
     * Representa um robô no ambiente
     * Representação visual: '0'
     */
    ROBO('0'),
    
    /**
     * Representa um obstáculo no ambiente
     * Representação visual: 'X'
     */
    OBSTACULO('X'),
    
    /**
     * Representa um tipo de entidade desconhecida
     * Representação visual: '?'
     */
    DESCONHECIDO('?');
    
    // Campo final que armazena o caractere de representação visual
    private final char representacao;  // Representação do tipo de entidade como um caractere

    /**
     * Construtor do enum que associa um caractere de representação a cada valor
     * @param representacao Caractere que será usado para representar visualmente este tipo
     */
    TipoEntidade(char representacao) {
        // Atribui o caractere recebido ao campo representacao
        this.representacao = representacao;  // Define a representação do tipo de entidade
    }

    /**
     * Método para obter o caractere de representação visual deste tipo
     * @return Caractere representativo do tipo de entidade
     */
    public char getRepresentacao() {
        // Retorna o caractere armazenado no campo representacao
        return representacao;  // Retorna a representação do tipo de entidade
    }
}