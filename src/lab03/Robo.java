// Importa a classe ArrayList da biblioteca java.util, que será usada para armazenar objetos em uma lista dinâmica
import java.util.ArrayList;

// Declaração da classe Robo, que representa um robô com posição e direção em um ou mais ambientes
public class Robo {
    // Declaração das variáveis privadas: nome, posição (X e Y), direção e lista de ambientes
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;
    
    // Lista de ambientes onde o robô pode estar presente
    private Ambiente ambiente;

    // Construtor da classe Robo, que inicializa o robô com nome, posições X e Y, e direção
    public Robo(String nome, int xIni, int yIni, String direcao) {
        setNome(nome);           // Inicializa o nome do robô
        setPosicaoX(xIni);       // Inicializa a posição X do robô
        setPosicaoY(yIni);       // Inicializa a posição Y do robô
        setDirecao(direcao);     // Inicializa a direção do robô
    }

    // Método getter para a lista de ambientes (retorna a lista de ambientes do robô)
    public Ambiente getAmbientes() {
        return ambiente;  // Retorna a lista de ambientes
    }

    // Método que adiciona um ambiente à lista de ambientes do robô
    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;  // Adiciona o ambiente à lista
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


    // Método que move o robô para uma nova posição, alterando suas coordenadas X e Y
    public void mover(int deltaX, int deltaY) {
        // Atualiza a posição X, garantindo que o valor seja maior ou igual a 0
        setPosicaoX(Math.max(getPosicaoX() + deltaX, 0)); 
        // Atualiza a posição Y, garantindo que o valor seja maior ou igual a 0
        setPosicaoY(Math.max(getPosicaoY() + deltaY, 0));  
        
        // Define a direção do robô com base no valor de deltaX
        this.direcao = (deltaX > 0 ? "Leste" : "Oeste");  
        
        // Atualiza a direção com base no valor de deltaY
        this.direcao = (deltaY > 0 ? "Norte" : "Sul");
    }

    // Método que identifica os obstáculos (outros robôs) nos ambientes em que o robô está
    public ArrayList<Robo> identificarObstaculo() {
        ArrayList<Robo> obstaculos = new ArrayList<>();  // Cria uma lista para armazenar os obstáculos

        // Para cada robô no ambiente, adiciona à lista de obstáculos
        for (Robo robo : ambiente.getRobos())
            obstaculos.add(robo);  // Adiciona o robô à lista de obstáculos
            
        return obstaculos;  // Retorna a lista de obstáculos
    }

    // Método que retorna a posição atual do robô como um par (X, Y)
    public Pair exibirPosicao() {
        return new Pair(getPosicaoX(), getPosicaoY());  // Retorna a posição como um par de valores
    }
}
