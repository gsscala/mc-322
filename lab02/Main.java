public class Main {
    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente(100, 200);
        RoboAleatorio roboaleatorio = new RoboAleatorio("dog", 0, 0, "Norte", 100);
        RoboTeletransportador roboteletransportador = new RoboTeletransportador("dog", 0, 0, "Norte", 100);
        RoboTerrestre roboterrestre = new RoboTerrestre("dog", 0, 0, "Norte", 100);
        RoboAereo roboaereo = new RoboAereo("dog", 0, 0, "Norte", 100);
        Robo robo = new Robo("dog", 0, 0, "Norte");

        ambiente.adicionarRobo(roboaleatorio);
        ambiente.adicionarRobo(roboteletransportador);
        ambiente.adicionarRobo(roboterrestre);
        ambiente.adicionarRobo(roboaereo);
        ambiente.adicionarRobo(robo);

        for (Robo r : ambiente.getRobos())
            r.mover(1, 1);

        for (RoboAereo r : new RoboAereo[]{roboaleatorio, roboteletransportador, roboaereo})
            r.subir(1);
        
        roboterrestre.mover(1000, 1000, 1000);

        roboaleatorio.mover();

        roboteletransportador.mover(1, 1);

        for (Robo obstaculo : robo.identificarObstaculo())
            System.out.println("Robo identificado: (" + obstaculo.exibirPosicao().first() + ", " + obstaculo.exibirPosicao().second() + ")");
    }
}
