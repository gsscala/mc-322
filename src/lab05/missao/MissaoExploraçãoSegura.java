package missao;
import java.util.logging.Logger;

import ambiente.Ambiente;
import entity.Entidade;
import robos.AgenteInteligente;
import utils.DistanceCalculator;
import utils.RandomNumberGenerator;
import robos.Robo;

public final class MissaoExploraçãoSegura implements Missao {

    AgenteInteligente robo;
    Ambiente ambiente;
    private final Logger logger;

    public Robo getRobo() {
        return robo;
    }   

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public Logger getLogger() {
        return logger;
    }

    public MissaoExploraçãoSegura(AgenteInteligente robo, Ambiente ambiente){
        this.robo = robo;
        this.ambiente = ambiente;
        logger = Logger.getLogger("logs/exploracao" + robo.getNome() + ".log");
    }
    
    public void executarMissao() {
        int numIt = new RandomNumberGenerator(10, 50).generate();

        for (int i = 0; i < numIt; i++) {
            int stepx = new RandomNumberGenerator(-1, 3).generate();
            int stepy = new RandomNumberGenerator(-2, 4).generate();

            getRobo().mover(stepx, stepy);
            getLogger().info("Passo " + i + ": Robô moveu-se para (" + getRobo().getPosicaoX() + ", " + getRobo().getPosicaoY() + ")");

            for (Entidade obstaculo : getAmbiente().getEntidades()) {
                DistanceCalculator distancia = new DistanceCalculator(robo, obstaculo);
                if (distancia.calculateDistance() <= 20) {
                    getLogger().warning("Obstáculo detectado a menos de 20 unidades. Missão abortada.");
                    return;
                }
            }
        }
    }
}