package robos;

import utils.RandomNumberGenerator;

import java.util.ArrayList;

import sensores.NaoSensoriavelException;
import sensores.Sensor;

public class RoboDistraido extends AgenteInteligente {

    private double nivelDistracao;
    
    private ArrayList<Sensor>sensores;
    
    public RoboDistraido(String nome, int xIni, int yIni, String direcao, double nivelDistracao) {
        super(nome, xIni, yIni, direcao);
        this.nivelDistracao = nivelDistracao;

        setSensores(SensorProximidade(new RandomNumberGenerator(1, 8).generate()));

        setSensores(SensorUmidade(new RandomNumberGenerator(1, 8).generate()));
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

    public Arraylist<Sensor> getSensores (){
        return sensores;
    }

    public setSensores(Sensor sensor){
        sensores.add(sensor);
    }

    public void setNivelDistracao(double nivelDistracao) {
        this.nivelDistracao = nivelDistracao;
    }

    public double getNivelDistracao() {
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
