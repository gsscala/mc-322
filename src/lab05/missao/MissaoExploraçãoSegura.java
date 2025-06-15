package missao;
import ambiente.Ambiente;
import robos.AgenteInteligente;
import sensores.NaoSensoriavelException;
import sensores.SensorProximidade;
import sensores.Sensoreavel;
import utils.RandomNumberGenerator;

public class MissaoExploraçãoSegura implements Missao {
    
    public void executar(AgenteInteligente robo, Ambiente ambiente) throws NaoSensoriavelException {

        if (! (robo instanceof SensorProximidade))
            throw new NaoSensoriavelException("Robô não consegue explorar de forma segura pois não consegue usar sensores");

        Sensoreavel sensoreavelRobo = (Sensoreavel) robo;

        bool valid = false;

        for (Sensor sensor : sensoreavelRobo.getSensores())
            valid |= (sensor instanceof SensorProximidade);
        
        if (! valid)
            throw new NaoSensoriavelException("Robô não consegue explorar de forma segura pois não consegue usar sensores");

        int numIt = new RandomNumberGenerator(10, 50).generate();

        for (int i = 0; i < numIt; i++){
            int stepx = new RandomNumberGenerator(0, 1).generate();
            if (stepx == 0)
                stepx = -1;
            
            int stepy = new RandomNumberGenerator(0, 1).generate();
            if (stepy == 0)
                stepy = -1;

            sensoreavelRobo.mover(stepx, stepy);

            sensoreavelRobo.acionarSensores();

            // nao acaba quando achar obstaculo
        }
    }
}