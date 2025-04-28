package ambiente;
import java.util.ArrayList;

import obstaculos.Obstaculo;
import robos.Robo;

// Declaração da classe Ambiente
public class Ambiente {
    // Declaração de variáveis privadas: largura e altura do ambiente
    private int largura; // eixos x e y (mapa quadrado)
    private int altura; // eixo z (""altura"")
    private String nome;
    
    // Criação de uma lista (ArrayList) para armazenar os objetos da classe Robo
    private ArrayList<Robo> robos = new ArrayList<>();

    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
    
    // Construtor da classe Ambiente, que inicializa largura e altura com valores fornecidos
    public Ambiente(int largura, int altura, String nome) {
        this.largura = largura;  // Atribui o valor de largura ao atributo da classe
        this.altura = altura;    // Atribui o valor de altura ao atributo da classe
        this.nome = nome;
    }

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

    // Método getter para a variável largura (retorna o valor da largura)
    public String getNome() {
        return nome;
    }

    // Método setter para a variável largura (define o valor da largura)
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método que adiciona um objeto da classe Robo à lista de robôs e associa o ambiente ao robô
    public void adicionarRobo(Robo r) {
        robos.add(r);           // Adiciona o robô à lista de robôs
        r.setAmbiente(this);    // Chama o método addAmbiente no objeto Robo para associar o ambiente a ele
    }

    public void removerRobo(Robo r) {
        robos.removeIf(robo -> robo == r);
    }
    // usar remover robo 

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

    public boolean handleColisoes(int posicaoX, int posicaoY, int altitude, Robo roboAtual) {
        // retorna true no caso de parada do movimento, senao retorna false e continua a movimento 

        for (Robo r : this.getRobos()) {
            if (posicaoX == r.getPosicaoX() && posicaoY == r.getPosicaoY() && altitude == r.getAltitude()) {
                System.out.println("Robô iria colidir com o robo" + r.getNome() + ", mas parou 1 casa antes de atigir ele");  
                return true;
            }
        }

        for (Obstaculo o : this.getObstaculos()) {
            // checa se esta nos bounds de um obstaculo
            if (o.dentroDosLimites(posicaoX, posicaoY, altitude)) {
                if (o.handleColisao(roboAtual)) {
                    return true;

                }
            }
        }

        return false;
    }

    public boolean hasObstacle(int posicaoX, int posicaoY, int altitude) {
        // Verifica colisão com robôs
        for (Robo r : this.getRobos()) {
            if (posicaoX == r.getPosicaoX() && posicaoY == r.getPosicaoY() && altitude == r.getAltitude()) {
                return true; // Existe robô na posição
            }
        }
    
        // Verifica colisão com obstáculos
        for (Obstaculo o : this.getObstaculos()) {
            if (o.dentroDosLimites(posicaoX, posicaoY, altitude)) {
                return true; // Existe obstáculo na posição
            }
        }
    
        return false; // Nada encontrado
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
