// Importa a classe ArrayList da biblioteca java.util, que será usada para armazenar objetos em uma lista dinâmica
import java.util.ArrayList;

// Declaração da classe Ambiente
public class Ambiente {
    // Declaração de variáveis privadas: largura e altura do ambiente
    private int largura;
    private int altura;
    
    // Criação de uma lista (ArrayList) para armazenar os objetos da classe Robo
    private ArrayList<Robo> robos = new ArrayList<>();

    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();

    // Método getter para a variável altura (retorna o valor da altura)
    public int getAltura() {
        return altura;
    }

    // Método setter para a variável altura (define o valor da altura)
    public void setAltura(int altura) {
        this.altura = altura;
    }

    // Método getter para a variável largura (retorna o valor da largura)
    public int getLargura() {
        return largura;
    }

    // Método setter para a variável largura (define o valor da largura)
    public void setLargura(int largura) {
        this.largura = largura;
    }

    // Construtor da classe Ambiente, que inicializa largura e altura com valores fornecidos
    public Ambiente(int largura, int altura) {
        this.largura = largura;  // Atribui o valor de largura ao atributo da classe
        this.altura = altura;    // Atribui o valor de altura ao atributo da classe
    }

    // Método que adiciona um objeto da classe Robo à lista de robôs e associa o ambiente ao robô
    public void adicionarRobo(Robo r) {
        robos.add(r);           // Adiciona o robô à lista de robôs
        r.setAmbiente(this);    // Chama o método addAmbiente no objeto Robo para associar o ambiente a ele
    }

    public void removerRobo(Robo r) {
        robos.removeIf(robo -> robo == r);
    }

    // Método getter que retorna a lista de robôs
    public ArrayList<Robo> getRobos() {
        return this.robos;  // Retorna a lista de robôs
    }

    public void adicionarObstaculo(Obstaculo o) {
        obstaculos.add(o); // Adiciona o obstáculo à lista
    }
    
    public void removerObstaculo(Obstaculo o) {
        obstaculos.removeIf(obstaculo -> obstaculo == o); // Remove o obstáculo exato
    }
    
    public ArrayList<Obstaculo> getObstaculos() {
        return this.obstaculos; // Retorna a lista de obstáculos
    }
    

    // Método que verifica se as coordenadas (x, y) estão dentro dos limites do ambiente
    public boolean dentroDosLimites(int x, int y) {
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura;  // Verifica se x e y estão dentro dos limites
    }

    // Sobrecarga do método dentroDosLimites que também verifica a coordenada z e a altitude máxima
    public boolean dentroDosLimites(int x, int y, int z, int altitudeMaxima) {
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura && z >= 0 && z < altitudeMaxima;
        // Verifica se x, y e z estão dentro dos limites do ambiente e se a altitude está dentro do limite máximo
    }
}
