public class RoboAereo extends Robo{
    private int altitude = 0;
    private int altitudeMaxima;

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }

    public RoboAereo (String nome, int xIni, int yIni, String direcao, int altitudeMaxima){
        super(nome, xIni, yIni, direcao);
        setAltitudeMaxima(altitudeMaxima);
    }

    public void subir(int deltaY){
        if (deltaY >= 0)
            setAltitude(getAltitude() + deltaY);
        else
            System.out.println("O número não pode ser negativo!");
    }

    public void descer(int deltaY){
        if (deltaY >= 0)
            setAltitude(getAltitude() - deltaY);
        else
            System.out.println("O número não pode ser negativo!");
    }
}