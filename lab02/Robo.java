public class Robo {
    private String nome;
    private int posicaoX;
    private int posicaoY;
    private String direcao

    public Robo (String nome, int xIni, int yIni, String direcao){
        this.nome = nome;
        this.posicaoX = xIni;
        this.posicaoY = yIni;
        this.direcao = direcao;
    }
    public void mover(int deltaX, int deltaY){
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }
    public String[] identificarObstaculo(){
        return;
    }
    public Pair exibirPosicao(){
        return new Pair(this.posicaoX, this.posicaoY);
    }
}
