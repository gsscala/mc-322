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
        if (velocidadeMaxima < 1){
            setVelocidadeMaxima(1);
            System.out.println("Velocidade máxima não pode ser menor do que 1");
        }
        else
            setVelocidadeMaxima(velocidadeMaxima);
    }

    public void mover(int deltaX, int deltaY, int velocidade){
        if (velocidade <= getVelocidadeMaxima())
            super.mover(deltaX, deltaY);
        else
            System.out.println("Não é permitido exceder a velocidade máxima!");
    }
}