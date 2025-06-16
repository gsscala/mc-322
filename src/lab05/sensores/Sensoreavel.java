// Declaração do pacote ao qual esta interface pertence
package sensores;

// Importação da exceção necessária para a assinatura do método
import robos.RoboDesligadoException;

/**
 * Interface que define a capacidade de um objeto ter seus sensores acionados.
 * Classes que implementam esta interface podem ativar uma coleção de sensores
 * para realizar monitoramento ambiental ou de entidades específicas.
 */
public interface Sensoreavel {    
    /**
     * Aciona todos os sensores associados ao objeto implementador.
     * 
     * @throws RoboDesligadoException Se o objeto estiver em estado inoperante (desligado ou inativo)
     *                                e não puder realizar o sensoriamento
     * @throws NaoSensoriavelException Se ocorrer uma falha específica durante o sensoriamento,
     *                                 como incompatibilidade com o alvo ou falha de leitura
     */
    void acionarSensores() throws RoboDesligadoException, NaoSensoriavelException;
}