package ambiente;
import java.util.ArrayList;

import obstaculos.ColisaoException;
import obstaculos.Obstaculo;
import robos.*;

import entity.*;

// Declaração da classe Ambiente
public class Ambiente {
    // Declaração de variáveis privadas: largura e altura do ambiente
    private int largura; // eixo x 
    private int profundidade; // eixo y 
    private int altura; // eixo z 
    private String nome;
    private int umidade;
    
    // Criação de uma lista (ArrayList) para armazenar os objetos da classe Robo
    // private ArrayList<Robo> robos = new ArrayList<>();

    // // @Deprecated
    // private ArrayList<Obstaculo> obstaculos = new ArrayList<>();

    private ArrayList<Entidade> entidades = new ArrayList<>();

    private TipoEntidade[][][] mapa; 

    
    // Construtor da classe Ambiente, que inicializa largura e altura com valores fornecidos
    public Ambiente(int largura, int profundidade, int altura, String nome, int umidade) {
        this.largura = largura;  // Atribui o valor de largura ao atributo da classe
        this.profundidade = profundidade; // Atribui o valor de profundidade ao atributo da classe
        this.altura = altura;    // Atribui o valor da altura ao atributo da classe
        this.nome = nome;
        this.umidade = umidade;

        inicializaMapa(); // Chama o método para inicializar o mapa
    }

    // Método getter para a variável altura (retorna o valor da altura)
    public int getAltura() {
        return altura;
    }

    // Método setter para a variável altura (define o valor da altura)
    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getProfundidade() {
        return profundidade;
    }
    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
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

    // Método getter para a variável umidade
    public int getUmidade() {
        return umidade;
    }

    // Método setter para a variável umidade
    public void setUmidade(int umidade) {
        this.umidade = umidade;
    }

    public void inicializaMapa() {
        mapa = new TipoEntidade[largura][profundidade][altura]; 

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < profundidade; y++) {
                for (int z = 0; z < altura; z++) {  
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }   

    public TipoEntidade[][][] getMapa() {
        return mapa;
    }


    public void visualizarAmbiente() {
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < profundidade; y++) {
                System.out.print(mapa[x][y][0].getRepresentacao()); 
            }
            System.out.println();
        }
    }

    public void moverRoboMapa(int xi, int yi, int zi, int xf, int yf, int zf) {
        getMapa()[xi][yi][zi] = TipoEntidade.VAZIO;  // Limpa a posição inicial no mapa
        getMapa()[xf][yf][zf] = TipoEntidade.ROBO;  // Define a nova posição do robô no mapa
    }



    // // Método que adiciona um objeto da classe Robo à lista de robôs e associa o ambiente ao robô
    // public void adicionarRobo(Robo r) {
    //     robos.add(r);           // Adiciona o robô à lista de robôs
    //     r.setAmbiente(this);    // Chama o método addAmbiente no objeto Robo para associar o ambiente a ele
    // }

    public void adicionarEntidade(Entidade e) {
        entidades.add(e); 
        e.setAmbiente(this);
    }

    public void removerEntidade(Entidade e) {
        entidades.removeIf(entidade -> entidade == e); // Remove a entidade exata
    }

    public ArrayList<Entidade> getEntidades() {
        return this.entidades; // Retorna a lista de entidades
    }

    // Implementação do estaOcupado
    public void handleColisoes(int posicaoX, int posicaoY, int altitude, Robo roboAtual) throws ColisaoException {
        // retorna true no caso de parada do movimento, senao retorna false e continua a movimento 

        for (Entidade e : this.getEntidades()) {
            if (e instanceof Robo) {
                Robo r = (Robo) e;
                if (posicaoX == r.getPosicaoX() && posicaoY == r.getPosicaoY() && altitude == r.getAltitude()) {
                    System.out.println("Robô iria colidir com o robo " + r.getNome() + ", mas parou 1 casa antes de atigir ele");  
                    throw new ColisaoException("Colisão com outro robô detectada!"); 
                }
            }
            if (e instanceof Obstaculo) {
                Obstaculo o = (Obstaculo) e;
                try {
                    o.handleColisao(roboAtual, posicaoX, posicaoY, altitude);
                } catch (ColisaoException ex) {
                    if(roboAtual.getEstado() == EstadoRobo.MORTO) 
                        removerEntidade(e);
                    throw ex;
                }
            }
        }

    }

    // Implementação de estaOcupado, que verifica se há algum obstáculo ou robô na posição (posicaoX, posicaoY, altitude)
    public boolean hasObstacle(int posicaoX, int posicaoY, int altitude) {

        for (Entidade e : this.getEntidades()) {
            if (e instanceof Robo) {
                Robo r = (Robo) e;
                if (posicaoX == r.getPosicaoX() && posicaoY == r.getPosicaoY() && altitude == r.getAltitude()) {
                    return true;
                }
            }

            if (e instanceof Obstaculo) {
                Obstaculo o = (Obstaculo) e;
                if (o.dentroDosLimites(posicaoX, posicaoY, altitude)) {
                    return true; // Retorna true se o obstáculo está dentro dos limites
                }
            }
        }
        return false;

    }
    
    // Método que verifica se as coordenadas (x, y) estão dentro dos limites do ambiente
    @Deprecated
    public boolean dentroDosLimites(int x, int y) {
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura;
    }

    // Sobrecarga do método dentroDosLimites que também verifica a coordenada z e a altitude máxima
    public void dentroDosLimites(int x, int y, int z) throws ForaMapaException {
        if (x >= 0 && x < getLargura() && y >= 0 && y < getProfundidade() && z >= 0 && z < getAltura()) {
            return;
        }
        throw new ForaMapaException("Coordenadas fora dos limites do ambiente: (" + x + ", " + y + ", " + z + ")");
    }
}
