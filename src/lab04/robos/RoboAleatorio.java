// Declaração do pacote ao qual esta classe pertence
package robos;

// Importações necessárias para a classe
import utils.RandomNumberGenerator;  // Utilitário para geração de números aleatórios
import java.util.List;               // Interface para listas dinâmicas
import comunicacao.ErroComunicacaoException;  // Exceção de comunicação
import utils.DistanceCalculator;     // Utilitário para cálculo de distância
import entity.Entidade;              // Interface base para entidades

/**
 * Classe que representa um robô aéreo com movimentos e ações aleatórias.
 * Estende RoboAereo e implementa a capacidade de explosão (Explodidor).
 */
public class RoboAleatorio extends RoboAereo implements Explodidor {

    /**
     * Construtor que inicializa o robô aleatório.
     * 
     * @param nome Identificação do robô
     * @param xIni Posição inicial X
     * @param yIni Posição inicial Y
     * @param direcao Direção inicial
     * @param altitudeMaxima Limite máximo de altitude
     */
    public RoboAleatorio(String nome, int xIni, int yIni, String direcao, int altitudeMaxima) {
        // Chama o construtor da superclasse RoboAereo
        super(nome, xIni, yIni, direcao, altitudeMaxima);
    }

    /**
     * Move o robô para uma posição aleatória válida no ambiente 3D.
     * Gera novas coordenadas até encontrar uma posição sem obstáculos.
     */
    public void mover() {
        // Obtém dimensões do ambiente
        int largura = getAmbiente().getLargura();
        int profundidade = getAmbiente().getProfundidade();
    
        // Cria geradores de números aleatórios para cada eixo
        RandomNumberGenerator RNGx = new RandomNumberGenerator(0, largura - 1);
        RandomNumberGenerator RNGy = new RandomNumberGenerator(0, profundidade - 1);
        RandomNumberGenerator RNGz = new RandomNumberGenerator(0, getAltitudeMaxima() - 1);
    
        // Variáveis para nova posição
        int nx, ny, nz;

        // Loop até encontrar posição livre
        do {
            nx = RNGx.generate();  // Gera X aleatório
            ny = RNGy.generate();  // Gera Y aleatório
            nz = RNGz.generate();  // Gera Z aleatório
        } while (getAmbiente().hasObstacle(nx, ny, nz)); // Verifica colisões

        // Atualiza o mapa do ambiente com a nova posição
        getAmbiente().moverRoboMapa(getPosicaoX(), getPosicaoY(), getAltitude(), nx, ny, nz);
        
        // Atualiza estado interno do robô
        setPosicaoX(nx);
        setPosicaoY(ny);
        setAltitude(nz);

        // Log do movimento
        System.out.println(getNome() + " teleportou para (" + nx + ", " + ny + ", " + nz + ")");
    }

    /**
     * Implementação da interface Explodidor.
     * Destrói todos os robôs dentro do raio especificado.
     * 
     * @param radius Raio de efeito da explosão
     */
    @Override
    public void explodir(int radius) {
        // Obtém todas as entidades do ambiente
        List<Entidade> entidades = this.getAmbiente().getEntidades();

        // Itera de trás para frente para evitar problemas ao remover itens
        for (int i = entidades.size() - 1; i >= 0; i--) {
            Entidade entidade = entidades.get(i);

            // Verifica se é um robô diferente deste e dentro do raio
            if (entidade instanceof Robo && entidade != this) {
                Robo outroRobo = (Robo) entidade;
                double distancia = new DistanceCalculator(entidade, this).calculateDistance();
                
                if (distancia <= radius) {
                    // Remove robô afetado
                    System.out.println(outroRobo.getNome() + " morreu na explosão de " + this.getNome() + "!");
                    entidades.remove(i);
                }
            }
        }
    }

    /**
     * Executa tarefas específicas para robôs aleatórios.
     * Adiciona suporte ao comando "explodir" além das tarefas herdadas.
     * 
     * @param tarefa Nome da tarefa a executar
     * @param args Argumentos adicionais
     */
    @Override
    public void executarTarefa(String tarefa, String[] args) 
        throws RoboDesligadoException, ErroComunicacaoException, TaskNotFoundException {
        
        // Verifica estado antes de executar
        if (getEstado() != EstadoRobo.LIGADO) {
            throw new RoboDesligadoException("Robô desligado não pode executar tarefas");
        }
        
        // Seleciona tarefa baseada no comando
        try{
            switch (tarefa) {
                case "roubar":
                    roubar();  // Tarefa herdada
                    break;
                case "explodir":
                    // Converte argumento para inteiro e executa explosão
                    explodir(Integer.parseInt(args[0]));
                    break;
                default:
                    // Tarefa não reconhecida
                    throw new TaskNotFoundException("Tarefa não encontrada: " + tarefa);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Número de argumentos insuficiente. Execute help para descobrir como rodar o comando corretamente!");
        }
    }
}