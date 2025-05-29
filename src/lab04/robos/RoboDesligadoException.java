// Declaração do pacote ao qual esta classe pertence
package robos;

/**
 * Exceção personalizada para representar situações onde uma operação
 * é tentada em um robô que está desligado ou inoperante.
 * Esta exceção é lançada quando um robô tenta executar uma ação
 * enquanto está no estado DESLIGADO ou MORTO.
 */
public class RoboDesligadoException extends Exception {
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param message Descrição detalhada do erro ocorrido
     */
    public RoboDesligadoException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem de erro
        super(message);  
    }
    
    /**
     * Construtor que cria uma exceção com mensagem e causa raiz.
     * 
     * @param message Descrição detalhada do erro ocorrido
     * @param cause Exceção original que causou este erro (para encadeamento)
     */
    public RoboDesligadoException(String message, Throwable cause) {
        // Chama o construtor da superclasse passando mensagem e causa
        super(message, cause);  
    }
}