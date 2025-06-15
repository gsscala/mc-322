package robos.subsistemas;

import ambiente.ForaMapaException;
import obstaculos.ColisaoException;
import robos.Robo;

/**
 * Subsistema responsável por controlar o movimento de um robô no ambiente.
 */
public class ControleMovimento {

    private Robo robo;

    /**
     * Cria um novo controlador de movimento para o robô informado.
     *
     * @param robo O robô que será controlado.
     */
    public ControleMovimento(Robo robo) {
        this.robo = robo;
    }

    /**
     * Define um novo robô a ser controlado.
     *
     * @param robo O novo robô.
     */
    public void setRobo(Robo robo) {
        this.robo = robo;
    }

    /**
     * Retorna o robô atual.
     *
     * @return O robô.
     */
    public Robo getRobo() {
        return robo;
    }

    /**
     * Move o robô no ambiente considerando variações nos eixos X e Y.
     * O movimento é realizado passo a passo, verificando colisões e limites do mapa.
     *
     * @param deltaX Variação no eixo X.
     * @param deltaY Variação no eixo Y.
     */
    public void mover(int deltaX, int deltaY) {
        int x = getRobo().getPosicaoX();
        int y = getRobo().getPosicaoY();
        int altitude = getRobo().getAltitude();

        int stepX = deltaX > 0 ? 1 : -1;
        int stepY = deltaY > 0 ? 1 : -1;

        int xf = x;
        int yf = y;

        // Movimento eixo X
        for (int dx = stepX; dx != deltaX + stepX; dx += stepX) {
            try {
                getRobo().getAmbiente().dentroDosLimites(x + dx, yf, altitude);
            } catch (ForaMapaException e) {
                System.err.println("Erro ao mover no eixo X: " + e.getMessage());
                xf = Math.max(0, Math.min(getRobo().getAmbiente().getLargura() - 1, x + dx));
                break;
            }

            try {
                getRobo().getAmbiente().handleColisoes(x + dx, y, altitude, getRobo());
            } catch (ColisaoException e) {
                System.err.println("Erro ao mover no eixo X: " + e.getMessage());
                xf = x + dx - stepX;
                break;
            }

            xf = x + dx;
        }

        // Movimento eixo Y
        for (int dy = stepY; dy != deltaY + stepY; dy += stepY) {
            try {
                getRobo().getAmbiente().dentroDosLimites(xf, y + dy, altitude);
            } catch (ForaMapaException e) {
                System.err.println("Erro ao mover no eixo Y: " + e.getMessage());
                yf = Math.max(0, Math.min(getRobo().getAmbiente().getProfundidade() - 1, y + dy));
                break;
            }

            try {
                getRobo().getAmbiente().handleColisoes(xf, y + dy, altitude, getRobo());
            } catch (ColisaoException e) {
                System.err.println("Erro ao mover no eixo Y: " + e.getMessage());
                yf = y + dy - stepY;
                break;
            }

            yf = y + dy;
        }

        // Atualiza posição do robô
        getRobo().setPosicaoX(xf);
        getRobo().setPosicaoY(yf);

        // Atualiza o mapa do ambiente
        getRobo().getAmbiente().moverRoboMapa(x, y, altitude, xf, yf, altitude);

        // Consome bateria proporcional à umidade
        int novaBateria = getRobo().getBateria() - getRobo().getAmbiente().getUmidade();
        getRobo().setBateria(novaBateria);

        // Atualiza direção no robô
        if (deltaX != 0) {
            getRobo().setDirecao(deltaX > 0 ? "Leste" : "Oeste");
        } else if (deltaY != 0) {
            getRobo().setDirecao(deltaY > 0 ? "Norte" : "Sul");
        }
    }
}
