package robos;

import utils.RandomNumberGenerator;
import missao.MissaoCentroide;
import missao.MissaoExploraçãoSegura;
import sensores.*;

public class RoboDistraido extends AgenteInteligente {

    private int nivelDistracao;

    public RoboDistraido(String nome, int xIni, int yIni, String direcao, int nivelDistracao) {
        super(nome, xIni, yIni, direcao);
        this.nivelDistracao = nivelDistracao;

        addSensor(new SensorProximidade(new RandomNumberGenerator(1, 8).generate()));
        // Para missões que envolvem sensoriamento local 
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

    public void executarMissao(String missao) throws TaskNotFoundException{
        if (new RandomNumberGenerator(0, 100).generate() > nivelDistracao) {
            try {
                switch (missao.toLowerCase()){
                    case "centroide":
                        MissaoCentroide centroide = new MissaoCentroide(this, getAmbiente());
                        centroide.executarMissao();
                        break;
                    case "exploracao":
                        MissaoExploraçãoSegura exploracao = new MissaoExploraçãoSegura(this, getAmbiente());
                        exploracao.executarMissao();
                        break;
                    default:
                        throw new TaskNotFoundException("Missão não encontrada");
                }
            } catch (NaoSensoriavelException e) {
                // Ignora exceção
            }
        }      

    }
    
}
