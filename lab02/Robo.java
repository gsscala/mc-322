public class Robo {
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao;

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
        setPosicaoX(getPosicaoX() + deltaX);
        setPosicaoY(getPosicaoY() + deltaY);
    }
    
    public String[] identificarObstaculo(){
        return;
    }
    public Pair exibirPosicao(){
        return new Pair(getPosicaoX(), getPosicaoY());
    }
}
