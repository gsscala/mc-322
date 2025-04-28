// A classe RoboAleatorio herda da classe RoboAereo e representa um robô aéreo que se move e altera sua altitude aleatoriamente

import ambiente.Ambiente;
import robos.RoboAereo;

public class RoboAleatorio extends RoboAereo {

    // Construtor da classe RoboAleatorio, que inicializa o robô com nome, posição (X e Y), direção e altitude máxima
    public RoboAleatorio(String nome, int xIni, int yIni, String direcao, int altitudeMaxima) {
        super(nome, xIni, yIni, direcao, altitudeMaxima);  // Chama o construtor da classe pai (RoboAereo) para inicializar nome, posição, direção e altitude máxima
    }

    // Método que faz o robô aleatório subir para uma altitude gerada aleatoriamente entre a altitude atual e a altitude máxima - 1
    public void subir() {
        RandomNumberGenerator RNG = new RandomNumberGenerator(getAltitude(), getAltitudeMaxima() - 1);  // Cria um gerador de números aleatórios para a faixa de altitudes
        setAltitude(RNG.generate());  // Gera uma nova altitude aleatória e a define no robô
    }

    // Método que faz o robô aleatório descer para uma altitude gerada aleatoriamente entre 0 e a altitude atual
    public void descer() {
        RandomNumberGenerator RNG = new RandomNumberGenerator(0, getAltitude());  // Cria um gerador de números aleatórios para a faixa de altitudes
        setAltitude(RNG.generate());  // Gera uma nova altitude aleatória e a define no robô
    }

    // Método que faz o robô aleatório se mover para uma nova posição aleatória dentro dos limites dos ambientes em que ele está
    public void mover() {
        int xmin = Integer.MAX_VALUE;  // Inicializa xmin com o valor máximo possível de um inteiro
        int ymin = Integer.MAX_VALUE;  // Inicializa ymin com o valor máximo possível de um inteiro

        // Para cada ambiente onde o robô está presente
        for (Ambiente ambiente : getAmbientes()) {
            // Define xmin e ymin como o menor valor entre o valor atual e o valor máximo permitido para as posições X e Y no ambiente
            xmin = Math.min(xmin, ambiente.getLargura() - 1);
            ymin = Math.min(ymin, ambiente.getAltura() - 1);
        }

        // Cria geradores de números aleatórios para as posições X e Y dentro dos limites definidos por xmin e ymin
        RandomNumberGenerator RNGx = new RandomNumberGenerator(0, xmin);
        RandomNumberGenerator RNGy = new RandomNumberGenerator(0, ymin);

        // Gera novas posições aleatórias para o robô dentro dos limites do ambiente
        setPosicaoX(RNGx.generate());  // Define a posição X aleatória
        setPosicaoY(RNGy.generate());  // Define a posição Y aleatória
    }
}
