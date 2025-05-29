// Declaração do pacote ao qual esta classe pertence
package ambiente;

// Declaração da classe de exceção personalizada
public class ForaMapaException extends Exception {
    
    // Construtor que recebe uma mensagem descritiva do erro
    public ForaMapaException(String message) {
        // Chama o construtor da superclasse (Exception) passando a mensagem
        super(message);
    }
}