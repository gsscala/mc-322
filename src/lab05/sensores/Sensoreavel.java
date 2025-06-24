// Declaração do pacote ao qual esta interface pertence
package sensores;

// Importação da exceção necessária para a assinatura do método
import robos.RoboDesligadoException;
import robos.subsistemas.GerenciadorSensores;


public interface Sensoreavel {    

    GerenciadorSensores getGerenciadorSensores(); // Require implementing classes to expose their module

    /**
     * Ativa todos os sensores do robô para monitoramento.
     * 
     * @throws RoboDesligadoException Se o robô estiver desligado
     */
    default void acionarSensores() throws RoboDesligadoException {
        getGerenciadorSensores().acionarSensores();
    }

    default void acionaSensor(String tipoSensor) {
        getGerenciadorSensores().acionaSensor(tipoSensor);
    }
}