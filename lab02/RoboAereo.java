public class RoboAereo extends Robo{
    private int altitude = 0;
    private int altitudeMaxima;

    public RoboAereo (String nome, int xIni, int yIni, String direcao, int altitudeMaxima){
        super(nome, xIni, yIni, direcao);
        this.altitudeMaxima = altitudeMaxima
    }

    public void subir(int deltaY){
        if (deltaY >= 0)
            this.altitude += deltaY;
    }

    public void descer(int deltaY){
        if (deltaY >= 0)
            this.altitude -= deltaY
    }
}