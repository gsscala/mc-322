package comunicacao;

public class ErroComunicacaoException extends Exception {
    // Construtor da classe ErroComunicacaoException
    public ErroComunicacaoException(String message) {
        super(message);  
    }   
    
    // Construtor da classe ErroComunicacaoException com causa
    public ErroComunicacaoException(String message, Throwable cause) {
        super(message, cause);  
    }
}