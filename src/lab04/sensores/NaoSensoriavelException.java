// Declaração do pacote ao qual esta classe pertence
package sensores;

/**
 * Exceção personalizada para representar situações onde uma entidade não pode ser sensoriada.
 * Esta exceção é lançada quando um sensor tenta monitorar ou interagir com uma entidade
 * que não possui capacidades de sensoriamento ou não é compatível com a operação do sensor.
 */
public class NaoSensoriavelException extends Exception {
    
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param message Descrição detalhada do erro ocorrido, incluindo informações
     *                sobre a entidade que não pôde ser sensoriada e o contexto
     *                da operação que falhou
     */
    public NaoSensoriavelException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem de erro
        super(message);
    }
}