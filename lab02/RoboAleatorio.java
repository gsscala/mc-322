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

    public void mover(){
        int xmin = (int)1e9;
        int ymin = (int)1e9;
        for (Ambiente ambiente : getAmbientes()){
            xmin = Math.min(xmin, ambiente.getLargura() - 1);
            ymin = Math.min(ymin, ambiente.getAltura() - 1);
        }

        RandomNumberGenerator RNGx = new RandomNumberGenerator(0, xmin);
        RandomNumberGenerator RNGy = new RandomNumberGenerator(0, ymin);

        setPosicaoX(RNGx.generate());
        setPosicaoY(RNGy.generate());
    }
}
