import java.util.ArrayList;

public class Robo {
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;
    protected ArrayList<Ambiente> ambientes = new ArrayList<>();

    public ArrayList<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void addAmbiente(Ambiente ambiente){
        ambientes.add(ambiente);
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPosicaoX(int posicaoX) {
        this.posicaoX = posicaoX;
    }
    public int getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoY(int posicaoY) {
        this.posicaoY = posicaoY;
    }
    public int getPosicaoY() {
        return posicaoY;
    }

    public Robo (String nome, int xIni, int yIni, String direcao){
        setNome(nome);
        setPosicaoX(xIni);
        setPosicaoY(yIni);
        setDirecao(direcao);
    }
    
    public void mover(int deltaX, int deltaY){
        setPosicaoX(Math.max(getPosicaoX() + deltaX, 0));
        setPosicaoY(Math.max(getPosicaoY() + deltaY, 0));
        this.direcao = (deltaX > 0 ? "Leste" : "Oeste");
        this.direcao = (deltaY > 0 ? "Norte" : "Sul");
    }
    
    public ArrayList<Robo> identificarObstaculo(){
        ArrayList<Robo>obstaculos = new ArrayList<>();
        for (Ambiente ambiente : getAmbientes())
            for (Robo robo : ambiente.getRobos())
                obstaculos.add(robo);
        return obstaculos;
    }
    
    public Pair exibirPosicao(){
        return new Pair(getPosicaoX(), getPosicaoY());
    }
}
