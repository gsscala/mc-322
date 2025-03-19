public class RoboTeletransportador extends RoboAereo{
    public RoboTeletransportador (String nome, int xIni, int yIni, String direcao, int altitudeMaxima){
        super(nome, xIni, yIni, direcao, altitudeMaxima);
    }

    public void subir(int pos){
        if (pos >= this.altitude)
            this.altitude = pos;
        else
            System.out.println("Posição desejada menor que a atual!");
    }
    
    public void descer(int pos){
        if (pos <= this.altitude)
            this.altitude = pos;
        else
            System.out.println("Posição desejada maior que a atual!");
    }
}
