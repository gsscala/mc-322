package robos;
// Importa a classe ArrayList da biblioteca java.util, que será usada para armazenar objetos em uma lista dinâmica

import java.util.ArrayList;

import ambiente.Ambiente;
import sensores.Sensor;
import utils.Pair;

// Declaração da classe Robo, que representa um robô com posição e direção em um ou mais ambientes
public class Robo {
    // Declaração das variáveis privadas: nome, posição (X e Y), direção e lista de ambientes
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;
    private boolean isVivo = true;

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
        setPosicaoX(0);
        setPosicaoY(0);
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
    
    public ArrayList<Sensor> getSensores() {
        return sensores;
    }
    public boolean getIsVivo() {
        return isVivo;  
    }

    public void setIsVivo(boolean isVivo) {
        this.isVivo = isVivo;  
    }

    // Método getter para a direção do robô
    public String getDirecao() {
        return direcao;  // Retorna a direção do robô
    }

    // Método setter para a direção do robô
    public void setDirecao(String direcao) {
        this.direcao = direcao;  // Define a direção do robô
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
    public int getPosicaoX() {
        return posicaoX;  // Retorna a posição X do robô
    }

    // Método setter para a posição Y do robô
    public void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;  // Define a posição Y do robô
    }

    // Método getter para a posição Y do robô
    public int getPosicaoY() {
        return posicaoY;  // Retorna a posição Y do robô
    }

    public int getAltitude() {
        return 0;
    }

    // Testa mover na ordem eixo X/Y
    public void mover(int deltaX, int deltaY) { 
        int x = getPosicaoX();
        int y = getPosicaoY();

        int stepX = deltaX > 0 ? 1 : -1; 
        int stepY = deltaY > 0 ? 1 : -1;

        for (int dx = stepX; dx != deltaX + stepX; dx += stepX) {
            
            if (x + dx == getAmbiente().getLargura()) {
                System.out.println("Chegou ao limite superior do eixo X");
                setPosicaoX(getAmbiente().getLargura()-1); // 0-indexado 

                break;
            }
            if (x + dx < 0) {
                System.out.println("Chegou ao limite inferior do eixo X");
                setPosicaoX(0);

                break;
            }

            if(ambiente.handleColisoes(x + dx, y, getAltitude(), this)) {
                int end_pos = x + dx - stepX;
                setPosicaoX(end_pos);

                if(!this.getIsVivo()) {
                    System.out.println("Robô morreu durante movimentação no eixo Y, escolha outro");
                }
                else {
                    System.out.println("Robô parou durante movimentação em X = " + end_pos);
                }

                break;
            }

            setPosicaoX(x + dx);
        }

        for (int dy = stepY; dy != deltaY + stepY; dy += stepY) {

            if (y + dy == getAmbiente().getLargura()) {
                System.out.println("Chegou ao limite superior do eixo Y");
                setPosicaoY(getAmbiente().getLargura()-1);

                break;
            }
            if (y + dy < 0) {
                System.out.println("Chegou ao limite inferior do eixo Y");
                setPosicaoY(0); // nem precisa

                break;
            }
    
            if (ambiente.handleColisoes(x, y + dy, getAltitude(), this)) {
                int end_pos = y + dy - stepY;
                setPosicaoY(end_pos);
    
                if (!this.getIsVivo()) {
                    System.out.println("Robô morreu durante movimentação no eixo Y, escolha outro");
                } else {
                    System.out.println("Robô parou durante movimentação em Y = " + end_pos);
                }
                
                break;
            }

            setPosicaoY(y + deltaY);
        }

        setBateria(getBateria() - getAmbiente().getUmidade());
        // perde um pouco de bateria devido a umidade do ar ao andar pelo vento



        // Define a direção do robô com base no valor de deltaX
        this.direcao = (deltaX > 0 ? "Leste" : "Oeste");  
        
        // Atualiza a direção com base no valor de deltaY
        this.direcao = (deltaY > 0 ? "Norte" : "Sul");
    }

    // Método que retorna a posição atual do robô como um par (X, Y)
    public Pair exibirPosicao() {
        return new Pair(getPosicaoX(), getPosicaoY());  // Retorna a posição como um par de valores
    }
}
