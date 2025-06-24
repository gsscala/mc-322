package missao;

import ambiente.Ambiente;
import entity.Entidade;
import logging.LoggerConfig;
import robos.AgenteInteligente;
import robos.EstadoRobo;
import robos.Robo;
import robos.RoboAtirador;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class MissaoMatador implements Missao {

    private final AgenteInteligente robo;
    private final Ambiente ambiente;
    private final Logger logger;

    public MissaoMatador(AgenteInteligente robo, Ambiente ambiente) {
        this.robo = robo;
        this.ambiente = ambiente;
        this.logger = LoggerConfig.getLogger("logs/matador_" + getRobo().getNome());
    }

   public Logger getLogger() {
        return logger;
    }

    public AgenteInteligente getRobo() {
        return robo;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    @Override
    public void executarMissao() {
        HashMap<Integer, Integer> x = new HashMap<>();
        HashMap<Integer, Integer> y = new HashMap<>();

        for (Entidade e : getAmbiente().getEntidades()) {
            if (e instanceof Robo outro) {
                int posX = outro.getPosicaoX();
                int posY = outro.getPosicaoY();
                x.put(posX, x.getOrDefault(posX, 0) + 1);
                y.put(posY, y.getOrDefault(posY, 0) + 1);
            }
        }

        int xCoord = getRobo().getPosicaoX();
        int xScore = 0;
        for (Map.Entry<Integer, Integer> entry : x.entrySet()) {
            if (entry.getValue() > xScore) {
                xScore = entry.getValue();
                xCoord = entry.getKey();
            }
        }

        int yCoord = getRobo().getPosicaoY();
        int yScore = 0;
        for (Map.Entry<Integer, Integer> entry : y.entrySet()) {
            if (entry.getValue() > yScore) {
                yScore = entry.getValue();
                yCoord = entry.getKey();
            }
        }

        getRobo().mover(xCoord - getRobo().getPosicaoX(), yCoord - getRobo().getPosicaoY());
        getLogger().info(String.format("Robô %s moveu-se para coordenada (%d, %d)", getRobo().getNome(), xCoord, yCoord));

        if (getRobo().getEstado() == EstadoRobo.MORTO) {
            getLogger().severe("Robô morreu em armadilha.");
            return;
        }

        if (getRobo().getPosicaoX() != xCoord || getRobo().getPosicaoY() != yCoord) {
            getLogger().severe("Robô falhou ao chegar e se autodestruiu.");
            getRobo().getAmbiente().removerEntidade(getRobo());
            return;
        }

        if (!(getRobo() instanceof RoboAtirador roboAtirador)) {
            getLogger().warning("Robô não é um atirador.");
            return;
        }

        String[][] direcoesFrases = {
            {"Norte", "O norte não será perdoado."},
            {"Leste", "A justiça vem do leste."},
            {"Sul", "O sul verá fogo e aço."},
            {"Oeste", "O oeste é onde termina sua sorte."}
        };

        for (String[] df : direcoesFrases) {
            roboAtirador.setDirecao(df[0]);
            getLogger().info(roboAtirador.getNome() + ": " + df[1]);
            roboAtirador.atirar();
        }

        getLogger().info("Missão matador finalizada com sucesso.");
    }
}