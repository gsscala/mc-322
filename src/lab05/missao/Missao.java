package missao;
import sensores.NaoSensoriavelException;

public interface Missao {
    void executarMissao() throws NaoSensoriavelException;
}