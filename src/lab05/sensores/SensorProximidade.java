// Declaração do pacote ao qual esta classe pertence
package sensores;

// Importações necessárias para a classe
import entity.Entidade;          // Interface base para entidades
import utils.DistanceCalculator; // Utilitário para cálculo de distância entre entidades

/**
 * Classe que representa um sensor de proximidade.
 * Estende a classe Sensor base e implementa funcionalidades específicas
 * para detecção de entidades próximas dentro de um raio determinado.
 */
public class SensorProximidade extends Sensor {

    /**
     * Construtor para o sensor de proximidade.
     * 
     * @param raio Alcance máximo de detecção do sensor (em unidades do ambiente)
     */
    public SensorProximidade(double raio) {
        // Chama o construtor da superclasse Sensor passando o raio de detecção
        super(raio);
    }

    /**
     * Implementação do método de monitoramento específico para sensor de proximidade.
     * Varre todas as entidades no ambiente e verifica aquelas dentro do raio de detecção.
     */
    @Override
    public void monitorar() {
        // Mensagem indicando a ativação do sensor
        System.out.println("Sensor de Proximidade ativado:");
        
        // Itera sobre todas as entidades no ambiente do robô associado
        for (Entidade obstaculo : getRobo().getAmbiente().getEntidades()) {
            // Cria um calculador de distância entre o robô e a entidade atual
            DistanceCalculator distancia = new DistanceCalculator(robo, obstaculo);

            // Verifica se a entidade está dentro do raio de detecção
            if (distancia.calculateDistance() <= getRaio()) {
                // Imprime alerta com a distância formatada
                System.out.printf("- Obstáculo detectado no raio de %.2f%n", getRaio());
            }
        }
    }
}