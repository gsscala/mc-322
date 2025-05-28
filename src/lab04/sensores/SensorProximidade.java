package sensores;

import entity.Entidade;
import utils.DistanceCalculator;

public class SensorProximidade extends Sensor {

    public SensorProximidade(double raio) {
        super(raio);
    }

    @Override
    public void monitorar() {
        System.out.println("Sensor de Proximidade ativado:");
        for (Entidade obstaculo : getRobo().getAmbiente().getEntidades()) {
            DistanceCalculator distancia = new DistanceCalculator(robo, obstaculo);

            if (distancia.calculateDistance() <= getRaio()) {
                System.out.printf("- Obstáculo detectado a %.2f unidades de distância.\n", distancia);
            }
        }
    }

}
