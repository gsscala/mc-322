public class Robo {
    private String nome;
    private int posicaoX;
    private int posicaoY;

    public Robo (String nome, int xIni, int yIni){
        this.nome = nome;
        this.posicaoX = xIni;
        this.posicaoY = yIni;
    }
    public void mover(int deltaX, int deltaY){
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }
    public Pair exibirPosicao(){
        return new Pair(this.posicaoX, this.posicaoY);
    }
}
