package robos;

public class RoboDesligadoException extends Exception {
    public RoboDesligadoException(String message) {
        super(message);  
    }
    
    // Construtor da classe RoboDesligadoException com causa
    public RoboDesligadoException(String message, Throwable cause) {
        super(message, cause);  
    }
}
