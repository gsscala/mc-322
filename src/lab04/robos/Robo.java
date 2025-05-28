package robos;
// Importa a classe ArrayList da biblioteca java.util, que será usada para armazenar objetos em uma lista dinâmica

import java.util.ArrayList;

import ambiente.Ambiente;
import ambiente.ForaMapaException;
import sensores.Sensor;
import entity.*;
import obstaculos.ColisaoException;

// Declaração da classe Robo, que representa um robô com posição e direção em um ou mais ambientes
public class Robo implements Entidade {
    // Declaração das variáveis privadas: nome, posição (X e Y), direção e lista de ambientes
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;
    private EstadoRobo estado = EstadoRobo.LIGADO;
    private String descricao = "Robô genérico";
    private TipoEntidade tipo = TipoEntidade.ROBO;
    private char representacao = '0';

    private int bateria = 2000; 
    
    // Lista de ambientes onde o robô pode estar presente
    private Ambiente ambiente;

    protected ArrayList<Sensor> sensores = new ArrayList<>();

    // Construtor da classe Robo, que inicializa o robô com nome, posições X e Y, e direção
    public Robo(String nome, int xIni, int yIni, String direcao) {
        setNome(nome);           // Inicializa o nome do robô
        setPosicaoX(xIni);       // Inicializa a posição X do robô
        setPosicaoY(yIni);       // Inicializa a posição Y do robô
        setDirecao(direcao);     // Inicializa a direção do robô
    }

    // Método getter para a lista de ambientes (retorna a lista de ambientes do robô)
    public Ambiente getAmbiente() {
        return ambiente;  // Retorna a lista de ambientes
    }

    // Método que adiciona um ambiente à lista de ambientes do robô
    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;  // Adiciona o ambiente à lista
        ambiente.getMapa()[getPosicaoX()][getPosicaoY()][getAltitude()] = TipoEntidade.ROBO; // Atualiza o mapa do ambiente com a posição do robô
    }

    public void addSensor(Sensor sensor) {
        for (Sensor s : sensores) {
            if (s.getClass().equals(sensor.getClass())) {
                System.out.println("Já existe um sensor do tipo " + sensor.getClass().getSimpleName() + " no robô.");
                return; 
            }
        }
        sensores.add(sensor);

        sensor.setRobo(this);
    }

    public void ligar() {
        estado = EstadoRobo.LIGADO;  // Define o estado do robô como ligado
    }
    
    public void desligar() {
        estado = EstadoRobo.DESLIGADO;  // Define o estado do robô como desligado
    }
    
    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    // Método getter para a direção do robô
    public String getDirecao() {
        return direcao;  // Retorna a direção do robô
    }

    // Método setter para a direção do robô
    public void setDirecao(String direcao) {
        this.direcao = direcao;  // Define a direção do robô
    }

    public EstadoRobo getEstado() {
        return estado;  
    }  

    public void setEstado(EstadoRobo estado) {
        this.estado = estado;  
    }

    // Método getter para o nome do robô
    public String getNome() {
        return nome;  // Retorna o nome do robô
    }

    // Método setter para o nome do robô
    public void setNome(String nome) {
        this.nome = nome;  // Define o nome do robô
    }

    // Método setter para a posição X do robô
    public void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;  // Define a posição X do robô
    }

    public int getBateria() {
        return bateria;  
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;  
    }

    // Método getter para a posição X do robô
    @Override
    public int getPosicaoX() {
        return posicaoX;  // Retorna a posição X do robô
    }

    // Método setter para a posição Y do robô
    public void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;  // Define a posição Y do robô
    }

    // Método getter para a posição Y do robô
    @Override
    public int getPosicaoY() {
        return posicaoY;  // Retorna a posição Y do robô
    }

    // Altitude irrelevante para robôs terrestres
    public int getAltitude() {
        return 0;
    }

    @Override
    public String getDescricao() {
        return descricao;  
    }

    @Override
    public TipoEntidade getTipo() {
        return tipo;  
    }

    @Override
    public char getRepresentacao() {
        return representacao;  
    }

    // Testa mover na ordem eixo X/Y
    public void mover(int deltaX, int deltaY) { 
        int x = getPosicaoX();
        int y = getPosicaoY();

        int stepX = deltaX > 0 ? 1 : -1; 
        int stepY = deltaY > 0 ? 1 : -1;

        int xf = x;
        int yf = y;

        for (int dx = stepX; dx != deltaX + stepX; dx += stepX) {
            try {
                getAmbiente().dentroDosLimites(x + dx, yf, getAltitude());

            } catch (ForaMapaException e) {
                System.err.println("Erro ao mover no eixo X: " + e.getMessage());
                int end_pos = Math.max(0, Math.min(getAmbiente().getLargura()-1, x + dx));
                xf = end_pos;

                break;
            }

            try {
                ambiente.handleColisoes(x + dx, y, getAltitude(), this);
            } catch (ColisaoException e) {
                System.err.println("Erro ao mover no eixo X: " + e.getMessage());
                int end_pos = x + dx - stepX;
                xf = end_pos;

                break;
            }

            xf = x + dx;
        }

        for (int dy = stepY; dy != deltaY + stepY; dy += stepY) {

            try {
                getAmbiente().dentroDosLimites(xf, y + dy, getAltitude());

            } catch (ForaMapaException e) {
                System.err.println("Erro ao mover no eixo Y: " + e.getMessage());
                int end_pos = Math.max(0, Math.min(getAmbiente().getProfundidade()-1, x + dy));
                yf = end_pos;

                break;
            }
    
            try {
                ambiente.handleColisoes(x, y + dy, getAltitude(), this);
            } catch (ColisaoException e) {
                System.err.println("Erro ao mover no eixo Y: " + e.getMessage());
                int end_pos = y + dy - stepY;
                yf = end_pos;
                
                break;
            }

            yf = y + dy;
        }

        setPosicaoX(xf);  // Atualiza a posição X do robô
        setPosicaoY(yf);  // Atualiza a posição Y do robô
        getAmbiente().moverRoboMapa(x, y, getAltitude(), xf, yf, getAltitude());  // Atualiza o mapa do ambiente com a nova posição do robô 

        setBateria(getBateria() - getAmbiente().getUmidade());
        // perde um pouco de bateria devido a umidade do ar ao andar pelo vento

        // Define a direção do robô com base no valor de deltaX
        this.direcao = (deltaX > 0 ? "Leste" : "Oeste");  
        
        // Atualiza a direção com base no valor de deltaY
        this.direcao = (deltaY > 0 ? "Norte" : "Sul");
    }

    // public void executarTarefa() {
    //     return;
    // }
}
