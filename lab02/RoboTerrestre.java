public class RoboTerrestre extends Robo {
    private int velocidadeMaxima;

    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public RoboTerrestre (String nome, int xIni, int yIni, String direcao, int velocidadeMaxima){
        super(nome, xIni, yIni, direcao);
        setVelocidadeMaxima(velocidadeMaxima);
    }

    public void mover(int deltaX, int deltaY, int velocidade){
        if (velocidade <= getVelocidadeMaxima())
            super.mover(deltaX, deltaY);
    }
}