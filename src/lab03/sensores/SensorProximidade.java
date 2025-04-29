package sensores;

import obstaculos.Obstaculo;
import java.util.List;

public class SensorProximidade extends Sensor {

    public SensorProximidade(double raio) {
        super(raio);
    }

    public void monitorar() {
        List<Obstaculo> obstaculos = getRobo().getAmbiente().getObstaculos();

        System.out.println("Sensor de Proximidade ativado:");
        for (Obstaculo obstaculo : obstaculos) {
            int centroX = (obstaculo.getPosicaoX1() + obstaculo.getPosicaoX2()) / 2;
            int centroY = (obstaculo.getPosicaoY1() + obstaculo.getPosicaoY2()) / 2;
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
