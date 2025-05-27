package sensores;

import entity.Entidade;

public class SensorProximidade extends Sensor {

    public SensorProximidade(double raio) {
        super(raio);
    }

    @Override
    public void monitorar() {
        System.out.println("Sensor de Proximidade ativado:");
        for (Entidade obstaculo : getRobo().getAmbiente().getEntidades()) {
            int centroX = obstaculo.getPosicaoX();
            int centroY = obstaculo.getPosicaoY();
            double distancia = calcularDistancia(robo.getPosicaoX(), robo.getPosicaoY(), centroX, centroY);

            if (distancia <= getRaio()) {
                System.out.printf("- Obstáculo detectado a %.2f unidades de distância.\n", distancia);
            }
        }
    }

    private double calcularDistancia(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
