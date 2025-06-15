// Declaração do pacote ao qual esta classe pertence
package robos;

/**
 * Exceção personalizada para representar situações onde uma tarefa solicitada não foi encontrada.
 * Esta exceção é lançada quando um robô tenta executar uma tarefa que não está disponível
 * em seu repertório de comandos ou não é suportada por sua implementação.
 */
public class TaskNotFoundException extends Exception {
    
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param message Descrição detalhada do erro ocorrido, incluindo informações
     *                sobre a tarefa que não foi encontrada
     */
    public TaskNotFoundException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem de erro
        super(message);
    }
}