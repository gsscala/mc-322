package missao;
import java.util.logging.Logger;

import ambiente.Ambiente;
import entity.Entidade;
import logging.LoggerConfig;
import robos.Robo;
import robos.AgenteInteligente;

public final class MissaoCentroide implements Missao {

    AgenteInteligente robo;
    Ambiente ambiente;
    private final Logger logger;


    public MissaoCentroide(AgenteInteligente robo, Ambiente ambiente){
        this.robo = robo;
        this.ambiente = ambiente;

        logger = LoggerConfig.getLogger("logs/centrodide_" + robo.getNome() + ".log");

    }

    public Logger getLogger() {
        return logger;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public AgenteInteligente getRobo() {
        return robo;
    }

    public void executarMissao() {
        int px = 0, py = 0, cnt = 0;
        for (Entidade e : getAmbiente().getEntidades()){
            if (!(e instanceof Robo) || e == robo) continue;
            px += e.getPosicaoX();
            py += e.getPosicaoY();
            cnt++;
            getLogger().info("Robô " + ((Robo) e).getNome() + " encontrado em (" + e.getPosicaoX() + ", " + e.getPosicaoY() + ")");
        }

        if (cnt == 0) {
            getLogger().info("Robô não encontrou nenhum outro robô para se mover com magnetismo.");
            return;
        }

        robo.mover(px / cnt, py / cnt);
        getLogger().info(String.format("Robô %s se moveu para o centroide: (%d, %d)", robo.getNome(), px / cnt, py / cnt));
    }
}