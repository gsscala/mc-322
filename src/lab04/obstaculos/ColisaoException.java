// Declaração do pacote ao qual esta classe pertence
package obstaculos;

/**
 * Exceção personalizada para representar situações de colisão no ambiente.
 * Esta exceção é lançada quando ocorre uma colisão entre entidades, como robôs e obstáculos.
 */
public class ColisaoException extends Exception {
    /**
     * Construtor que cria uma exceção de colisão com mensagem específica
     * @param message Descrição detalhada da colisão ocorrida
     */
    public ColisaoException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem de erro
        super(message);  
    }
}