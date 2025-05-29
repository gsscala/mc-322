// Declaração do pacote ao qual esta classe pertence
package sensores;

/**
 * Classe que representa um sensor de umidade ambiental.
 * Estende a classe Sensor base e implementa funcionalidades específicas
 * para medição da umidade do ambiente onde o robô está localizado.
 */
public class SensorUmidade extends Sensor {

    /**
     * Construtor para o sensor de umidade.
     * 
     * @param raio Parâmetro herdado da superclasse (não utilizado nesta implementação específica,
     *             mas mantido para consistência com a hierarquia de sensores)
     */
    public SensorUmidade(double raio) {
        // Chama o construtor da superclasse Sensor
        super(raio);
        // Nota: Embora o raio não seja utilizado neste sensor específico,
        // mantemos o parâmetro para manter a consistência com outros sensores
    }

    /**
     * Implementação do método de monitoramento para sensor de umidade.
     * Obtém a umidade atual do ambiente diretamente das propriedades ambientais.
     */
    @Override
    public void monitorar() {
        // Obtém o valor de umidade do ambiente associado ao robô
        double umidade = robo.getAmbiente().getUmidade();
        
        // Exibe a leitura formatada com duas casas decimais
        System.out.printf("Sensor de Umidade ativado: Umidade atual do ambiente é %.2f\n", umidade);
    }
}