// Declaração do pacote ao qual esta classe pertence
package robos;

/**
 * Exceção personalizada para representar situações onde um robô específico não foi encontrado.
 * Esta exceção é lançada quando uma operação tenta acessar ou manipular um robô
 * que não existe no ambiente ou contexto atual.
 */
public class RoboNotFoundException extends Exception {
    
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param message Descrição detalhada do erro ocorrido, incluindo informações
     *                sobre o robô que não foi encontrado (ex: nome ou ID)
     */
    public RoboNotFoundException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem de erro
        super(message);
    }
}