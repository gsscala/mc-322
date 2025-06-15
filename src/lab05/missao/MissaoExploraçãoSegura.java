package missao;
import ambiente.Ambiente;
import robos.AgenteInteligente;
import sensores.NaoSensoriavelException;
import sensores.Sensoreavel;

public class MissaoExploraçãoSegura implements Missao {
    
    public void executar(AgenteInteligente robo, Ambiente ambiente) throws NaoSensoriavelException {


        if (robo instanceof Sensoreavel) {
            Sensoreavel sensoreavelRobo = (Sensoreavel) robo;
        }
        else {
            throw new NaoSensoriavelException("Robô não consegue explorar de forma segura pois não consegue usar sensores");
        }   
    }
}
