package missao;
import ambiente.Ambiente;
import robos.AgenteInteligente;
import sensores.*;
import utils.RandomNumberGenerator;
import robos.Robo;

public final class MissaoExploraçãoSegura implements Missao {

    AgenteInteligente robo;
    Ambiente ambiente;

    public MissaoExploraçãoSegura(AgenteInteligente robo, Ambiente ambiente){
        this.robo = robo;
        this.ambiente = ambiente;
    }
    
    public void executarMissao() throws NaoSensoriavelException {

        if (! (robo instanceof Sensoreavel))
            throw new NaoSensoriavelException("Robô não consegue explorar de forma segura pois não consegue usar sensores");

        Sensoreavel sensoreavelRobo = (Sensoreavel) robo;

        Boolean valid = false;

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

            ((Robo) robo).mover(stepx, stepy);

            try{
                sensoreavelRobo.acionarSensores();
            }catch (Exception e){
                System.err.println(e);
            }

            // nao acaba quando achar obstaculo
        }
    }
}