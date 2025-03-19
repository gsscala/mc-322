public class RoboTerrestre extends Robo {
    private int velocidadeMaxima;
    public RoboTerrestre (String nome, int xIni, int yIni, String direcao, int velocidadeMaxima){
        super(nome, xIni, yIni, direcao);
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public void mover(int deltaX, int deltaY, int velocidade){
        if (velocidade <= this.velocidadeMaxima)
            super.mover(deltaX, deltaY);
    }
}