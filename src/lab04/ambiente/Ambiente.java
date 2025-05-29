// Define o pacote ao qual esta classe pertence
package ambiente;

// Importa classes necessárias
import java.util.ArrayList;
import obstaculos.ColisaoException;
import obstaculos.Obstaculo;
import robos.*;
import comunicacao.CentralComunicacao;
import entity.*;

// Declaração da classe que representa o ambiente de simulação
public class Ambiente {
    // Dimensões do ambiente (coordenadas 3D)
    private int largura;     // Eixo X (horizontal)
    private int profundidade; // Eixo Y (profundidade)
    private int altura;       // Eixo Z (vertical)
    private String nome;      // Identificação do ambiente
    private int umidade;      // Nível de umidade ambiental
    
    // Lista dinâmica para armazenar todas as entidades do ambiente (robôs e obstáculos)
    private ArrayList<Entidade> entidades = new ArrayList<>();

    // Mapa 3D que representa o ambiente, onde cada posição armazena o tipo de entidade
    private TipoEntidade[][][] mapa; 

    // Referência para a central de comunicação do ambiente
    private CentralComunicacao centralComunicacao;

    // Construtor que inicializa o ambiente com parâmetros básicos
    public Ambiente(int largura, int profundidade, int altura, String nome, int umidade) {
        // Atribui valores iniciais às dimensões
        this.largura = largura;
        this.profundidade = profundidade;
        this.altura = altura;
        this.nome = nome;
        this.umidade = umidade;

        // Inicializa a estrutura do mapa 3D
        inicializaMapa();
    }

    // --- MÉTODOS ACESSORES (getters) ---
    public int getAltura() {
        return altura;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public int getLargura() {
        return largura;
    }

    public String getNome() {
        return nome;
    }

    public int getUmidade() {
        return umidade;
    }

    // --- MÉTODOS MODIFICADORES (setters) ---
    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUmidade(int umidade) {
        this.umidade = umidade;
    }

    // Inicializa o mapa 3D preenchendo todas as posições como vazias
    public void inicializaMapa() {
        // Aloca memória para o mapa baseado nas dimensões
        mapa = new TipoEntidade[largura][profundidade][altura]; 

        // Percorre todas as posições do mapa
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < profundidade; y++) {
                for (int z = 0; z < altura; z++) {  
                    // Define cada célula como vazia
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }   

    // Retorna a referência para o mapa 3D
    public TipoEntidade[][][] getMapa() {
        return mapa;
    }

    // Exibe uma representação visual do ambiente (camada Z=0)
    public void visualizarAmbiente() {
        // Percorre o plano XY na altitude 0
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < profundidade; y++) {
                // Imprime o caractere representativo da entidade
                System.out.print(mapa[x][y][0].getRepresentacao()); 
            }
            // Nova linha após cada linha do eixo X
            System.out.println();
        }
    }

    // Atualiza a posição de um robô no mapa
    public void moverRoboMapa(int xi, int yi, int zi, int xf, int yf, int zf) {
        // Limpa a posição anterior
        getMapa()[xi][yi][zi] = TipoEntidade.VAZIO;
        // Marca a nova posição com o robô
        getMapa()[xf][yf][zf] = TipoEntidade.ROBO;
    }

    // Associa uma central de comunicação ao ambiente
    public void adicionarCentralComunicacao(CentralComunicacao central) {
        this.centralComunicacao = central;
    }

    // Retorna a central de comunicação associada
    public CentralComunicacao getCentralComunicacao() {
        return this.centralComunicacao;
    }

    // Adiciona uma nova entidade ao ambiente
    public void adicionarEntidade(Entidade e) {
        entidades.add(e); 
        // Configura o ambiente atual da entidade
        e.setAmbiente(this);
    }

    // Remove uma entidade específica do ambiente
    public void removerEntidade(Entidade e) {
        // Utiliza expressão lambda para remoção segura
        entidades.removeIf(entidade -> entidade == e);
    }

    // Retorna a lista de todas as entidades
    public ArrayList<Entidade> getEntidades() {
        return this.entidades;
    }

    // Verifica e trata possíveis colisões em uma posição específica
    public void handleColisoes(int posicaoX, int posicaoY, int altitude, Robo roboAtual) throws ColisaoException {
        // Verifica todas as entidades
        for (Entidade e : this.getEntidades()) {
            // Verificação de colisão com outros robôs
            if (e instanceof Robo) {
                Robo r = (Robo) e;
                if (posicaoX == r.getPosicaoX() && posicaoY == r.getPosicaoY() && altitude == r.getAltitude()) {
                    System.out.println("Robô iria colidir com o robo " + r.getNome() + ", mas parou 1 casa antes de atingir ele");  
                    throw new ColisaoException("Colisão com outro robô detectada!"); 
                }
            }
            // Verificação de colisão com obstáculos
            if (e instanceof Obstaculo) {
                Obstaculo o = (Obstaculo) e;
                try {
                    // Delega o tratamento ao obstáculo
                    o.handleColisao(roboAtual, posicaoX, posicaoY, altitude);
                } catch (ColisaoException ex) {
                    // Remove obstáculo se o robô foi destruído
                    if(roboAtual.getEstado() == EstadoRobo.MORTO) 
                        removerEntidade(e);
                    throw ex;
                }
            }
        }
    }

    // Verifica se existe algum obstáculo em determinada posição
    public boolean hasObstacle(int posicaoX, int posicaoY, int altitude) {
        for (Entidade e : this.getEntidades()) {
            // Checa robôs
            if (e instanceof Robo) {
                Robo r = (Robo) e;
                if (posicaoX == r.getPosicaoX() && posicaoY == r.getPosicaoY() && altitude == r.getAltitude()) {
                    return true;
                }
            }

            // Checa obstáculos
            if (e instanceof Obstaculo) {
                Obstaculo o = (Obstaculo) e;
                if (o.dentroDosLimites(posicaoX, posicaoY, altitude)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // Método obsoleto para verificação 2D de limites (não considera altitude)
    @Deprecated
    public boolean dentroDosLimites(int x, int y) {
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura;
    }

    // Verifica se uma posição 3D está dentro dos limites do ambiente
    public void dentroDosLimites(int x, int y, int z) throws ForaMapaException {
        if (x >= 0 && x < getLargura() && 
            y >= 0 && y < getProfundidade() && 
            z >= 0 && z < getAltura()) {
            return;
        }
        // Lança exceção personalizada se fora dos limites
        throw new ForaMapaException("Coordenadas fora dos limites do ambiente: (" + x + ", " + y + ", " + z + ")");
    }
}