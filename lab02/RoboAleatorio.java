public class RoboAleatorio extends RoboAereo{
    public RoboAleatorio (String nome, int xIni, int yIni, String direcao, int altitudeMaxima){
        super(nome, xIni, yIni, direcao, altitudeMaxima);
    }

    public void subir(){
        RandomNumberGenerator RNG = new RandomNumberGenerator(this.altitude, this.altitudeMaxima);
        this.altitude = RNG.generate();
    }

    public void descer(){
        RandomNumberGenerator RNG = new RandomNumberGenerator(0, this.altitude);
        this.altitude = RNG.generate();
    }
}
