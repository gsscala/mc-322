package robos;
// A classe RoboAleatorio herda da classe RoboAereo e representa um robô aéreo que se move e altera sua altitude aleatoriamente

import utils.RandomNumberGenerator;

import java.util.List;

import comunicacao.ErroComunicacaoException;
import utils.DistanceCalculator;
import entity.Entidade;

public class RoboAleatorio extends RoboAereo implements Explodidor{

    // Construtor da classe RoboAleatorio, que inicializa o robô com nome, posição (X e Y), direção e altitude máxima
    public RoboAleatorio(String nome, int xIni, int yIni, String direcao, int altitudeMaxima) {
        super(nome, xIni, yIni, direcao, altitudeMaxima);  // Chama o construtor da classe pai (RoboAereo) para inicializar nome, posição, direção e altitude máxima
    }

    public void mover() {
        int largura = getAmbiente().getLargura();
        int profundidade = getAmbiente().getProfundidade();
    
        RandomNumberGenerator RNGx = new RandomNumberGenerator(0, largura - 1);
        RandomNumberGenerator RNGy = new RandomNumberGenerator(0, profundidade - 1);
        RandomNumberGenerator RNGz = new RandomNumberGenerator(0, getAltitudeMaxima() - 1);
    
        int nx, ny, nz;
    
        do {
            nx = RNGx.generate();
            ny = RNGy.generate();
            nz = RNGz.generate();
        } while (getAmbiente().hasObstacle(nx, ny, nz)); // Continua tentando enquanto houver colisão

        getAmbiente().moverRoboMapa(getPosicaoX(), getPosicaoY(), getAltitude(), nx, ny, nz); // Mover no mapa para a nova posição
        
        setPosicaoX(nx);
        setPosicaoY(ny);
        setAltitude(nz);

        System.out.println(getNome() + " teleportou para (" + getPosicaoX() + ", " + getPosicaoY() + ", " + getAltitude() + ")");
    }

    public void explodir(int radius) {
        List<Entidade> entidades = this.getAmbiente().getEntidades();

        for (int i = entidades.size() - 1; i >= 0; i--) {
            Entidade robo = entidades.get(i);

            if (robo instanceof Robo && new DistanceCalculator(robo, this).calculateDistance() <= radius) {
                System.out.println(((Robo) robo).getNome() + "morreu na explosão de " + this.getNome() + "!");
                entidades.remove(i);
            }
        }
    }

    public void executarTarefa(String tarefa, String[] args) throws RoboDesligadoException, ErroComunicacaoException, TaskNotFoundException {
        switch (tarefa) {
            case "roubar":
                roubar();
                break;
            case "explodir":
                explodir(Integer.parseInt(args[0]));
                break;
            default:
                throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);  // Lança exceção se a tarefa não for reconhecida
        }
    }
    
}
