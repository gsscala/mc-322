package missao;
import robos.AgenteInteligente;
import sensores.NaoSensoriavelException;
import ambiente.Ambiente;

public interface Missao {
    void executar(AgenteInteligente robo, Ambiente ambiente) throws NaoSensoriavelException;
}