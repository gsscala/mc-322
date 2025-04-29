package robos;
// A classe RoboAleatorio herda da classe RoboAereo e representa um robô aéreo que se move e altera sua altitude aleatoriamente

import utils.RandomNumberGenerator;

public class RoboAleatorio extends RoboAereo {

    // Construtor da classe RoboAleatorio, que inicializa o robô com nome, posição (X e Y), direção e altitude máxima
    public RoboAleatorio(String nome, int xIni, int yIni, String direcao, int altitudeMaxima) {
        super(nome, xIni, yIni, direcao, altitudeMaxima);  // Chama o construtor da classe pai (RoboAereo) para inicializar nome, posição, direção e altitude máxima
    }

    public void mover() {
        int largura = getAmbiente().getLargura();
        int altura = getAmbiente().getAltura();
    
        RandomNumberGenerator RNGx = new RandomNumberGenerator(0, largura - 1);
        RandomNumberGenerator RNGy = new RandomNumberGenerator(0, largura - 1);
        RandomNumberGenerator RNGz = new RandomNumberGenerator(0, altura - 1);
    
        int nx, ny, nz;
    
        do {
            nx = RNGx.generate();
            ny = RNGy.generate();
            nz = RNGz.generate();
        } while (getAmbiente().hasObstacle(nx, ny, nz)); // Continua tentando enquanto houver colisão
    
        setPosicaoX(nx);
        setPosicaoY(ny);
        setAltitude(nz);
    
        System.out.println(getNome() + " teleportou para (" + getPosicaoX() + ", " + getPosicaoY() + ", " + getAltitude() + ")");
    }
    
}
