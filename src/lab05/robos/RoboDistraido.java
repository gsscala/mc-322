package robos;

import utils.RandomNumberGenerator;
import sensores.*;

public class RoboDistraido extends AgenteInteligente {

    private int nivelDistracao;

    public RoboDistraido(String nome, int xIni, int yIni, String direcao, int nivelDistracao) {
        super(nome, xIni, yIni, direcao);
        this.nivelDistracao = nivelDistracao;

        addSensor(new SensorProximidade(new RandomNumberGenerator(1, 8).generate()));

    }

    /**
     * Ativa todos os sensores do robô para monitoramento.
     * 
     * @throws RoboDesligadoException Se o robô estiver desligado
     */
    public void acionarSensores() throws RoboDesligadoException {
        // Verifica se o robô está ligado
        if (getEstado() == EstadoRobo.LIGADO) {
            // Ativa cada sensor individualmente
            for (Sensor sensor : this.getSensores()) {
                sensor.monitorar();
            }
        } else {
            throw new RoboDesligadoException("Robô desligado não consegue usar sensores");
        }
    }

    public void setNivelDistracao(int nivelDistracao) {
        this.nivelDistracao = nivelDistracao;
    }

    public int getNivelDistracao() {
        return nivelDistracao;
    }

    public void executarMissao() {
        if (new RandomNumberGenerator(0, 100).generate() > nivelDistracao) {

            try {
                getMissao().executar(this, getAmbiente());
            } catch (NaoSensoriavelException e) {
                // Ignora exceção
            }
        }      

    }
    
}
