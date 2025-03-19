public class RoboAleatorio extends RoboAereo{
    public RoboAleatorio (String nome, int xIni, int yIni, String direcao, int altitudeMaxima){
        super(nome, xIni, yIni, direcao, altitudeMaxima);
    }

    public void subir(){
        RandomNumberGenerator RNG = new RandomNumberGenerator(getAltitude(), getAltitudeMaxima() - 1);
        setAltitude(RNG.generate());
    }

    public void descer(){
        RandomNumberGenerator RNG = new RandomNumberGenerator(0, getAltitude());
        setAltitude(RNG.generate());
    }
}
