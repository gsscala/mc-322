package sensores;

public class SensorUmidade extends Sensor {

    public SensorUmidade(double raio) {
        super(raio);
    }

    public void monitorar() {
        double umidade = robo.getAmbiente().getUmidade();
        System.out.printf("Sensor de Umidade ativado: Umidade atual do ambiente é %.2f\n", umidade);
    }
}
