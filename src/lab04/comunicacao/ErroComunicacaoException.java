// Declaração do pacote ao qual esta classe pertence
package comunicacao;

// Definição da classe de exceção personalizada para erros de comunicação
public class ErroComunicacaoException extends Exception {
    
    /**
     * Construtor que cria uma exceção com mensagem de erro específica
     * @param message Descrição detalhada do erro ocorrido
     */
    public ErroComunicacaoException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem
        super(message);  
    }   
    
    /**
     * Construtor que cria uma exceção com mensagem e causa raiz
     * @param message Descrição detalhada do erro ocorrido
     * @param cause Exceção original que causou este erro (para encadeamento)
     */
    public ErroComunicacaoException(String message, Throwable cause) {
        // Chama o construtor da superclasse passando mensagem e causa
        super(message, cause);  
    }
}