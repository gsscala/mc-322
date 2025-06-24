package robos.subsistemas;

import robos.Robo;
import robos.EstadoRobo;
import robos.RoboDesligadoException;
import sensores.Sensor;

public class GerenciadorSensores {

    private Robo robo;

    public GerenciadorSensores(Robo robo) {
        this.robo = robo;
    }

    /**
     * Define um novo robô a ser controlado.
     *
     * @param robo O novo robô.
     */
    public void setRobo(Robo robo) {
        this.robo = robo;
    }

    /**
     * Retorna o robô atual.
     *
     * @return O robô.
     */
    public Robo getRobo() {
        return robo;
    }

    /**
     * Ativa todos os sensores do robô para monitoramento.
     * 
     * @throws RoboDesligadoException Se o robô estiver desligado
     */
    public void acionarSensores() throws RoboDesligadoException {
        // Verifica se o robô está ligado
        if (getRobo().getEstado() == EstadoRobo.LIGADO) {
            // Ativa cada sensor individualmente
            for (Sensor sensor : getRobo().getSensores()) {
                sensor.monitorar();
            }
        } else {
            throw new RoboDesligadoException("Robô desligado não consegue usar sensores");
        }
    } 

    /**
    * Ativa todos os sensores do robô para monitoramento.
    * 
    * @throws RoboDesligadoException Se o robô estiver desligado
    */
    public void acionaSensor(String tipoSensor) {
        // Ativa cada sensor individualmente
        for (Sensor sensor : getRobo().getSensores()) {
            
            if (sensor.getClass().getSimpleName().equals(tipoSensor)) {
                sensor.monitorar();
                return; // Sai do loop após encontrar e ativar o sensor
            }
        }
    } 
}